package com.pdxx.kotlinlearn.activity

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.R

/**
 * Bluetooth Low Energy (BLE) 扫描示例
 *
 * 演示功能：
 * 1. 动态权限申请 (Android 12+ 和 Android 6-11 的区别)
 * 2. 开启蓝牙扫描
 * 3. 处理扫描结果
 */
class BleActivity : AppCompatActivity() {

    private lateinit var btnScan: Button
    private lateinit var btnStopScan: Button
    private lateinit var rvDevices: RecyclerView
    private lateinit var deviceAdapter: DeviceAdapter

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private var isScanning = false
    private val handler = Handler(Looper.getMainLooper())
    private val SCAN_PERIOD: Long = 10000 // 扫描 10 秒后停止

    // 权限请求 Launcher
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                startScan()
            } else {
                Toast.makeText(this, "需要蓝牙和定位权限才能扫描设备", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble)

        initViews()
    }

    private fun initViews() {
        btnScan = findViewById(R.id.btn_scan)
        btnStopScan = findViewById(R.id.btn_stop_scan)
        rvDevices = findViewById(R.id.rv_devices)

        deviceAdapter = DeviceAdapter()
        rvDevices.layoutManager = LinearLayoutManager(this)
        rvDevices.adapter = deviceAdapter

        btnScan.setOnClickListener {
            checkPermissionsAndStartScan()
        }

        btnStopScan.setOnClickListener {
            stopScan()
        }
    }

    private fun checkPermissionsAndStartScan() {
        // Android 12 (API 31) 及以上需要 BLUETOOTH_SCAN, BLUETOOTH_CONNECT
        // Android 6 - 11 需要 ACCESS_FINE_LOCATION
        val permissionsToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionsToRequest.add(Manifest.permission.BLUETOOTH_SCAN)
            permissionsToRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        val missingPermissions = permissionsToRequest.filter {
            ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isEmpty()) {
            startScan()
        } else {
            requestPermissionLauncher.launch(missingPermissions.toTypedArray())
        }
    }

    @SuppressLint("MissingPermission") // 权限已在 checkPermissionsAndStartScan 中检查
    private fun startScan() {
        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
            Toast.makeText(this, "请先开启蓝牙", Toast.LENGTH_SHORT).show()
            return
        }

        if (isScanning) return

        isScanning = true
        btnScan.isEnabled = false
        btnStopScan.isEnabled = true
        deviceAdapter.clear()

        Toast.makeText(this, "开始扫描...", Toast.LENGTH_SHORT).show()

        // 10秒后自动停止
        handler.postDelayed({
            stopScan()
        }, SCAN_PERIOD)

        bluetoothAdapter?.bluetoothLeScanner?.startScan(scanCallback)
    }

    @SuppressLint("MissingPermission")
    private fun stopScan() {
        if (!isScanning) return

        isScanning = false
        btnScan.isEnabled = true
        btnStopScan.isEnabled = false
        
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
        Toast.makeText(this, "扫描已停止", Toast.LENGTH_SHORT).show()
    }

    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val device = result.device
            val rssi = result.rssi
            // 过滤掉没有名字的设备 (可选)
            // if (device.name.isNullOrEmpty()) return
            
            deviceAdapter.addDevice(device, rssi)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e("BleActivity", "Scan failed with error: $errorCode")
            Toast.makeText(this@BleActivity, "扫描失败: $errorCode", Toast.LENGTH_SHORT).show()
        }
    }

    // 简单的 Adapter 显示设备列表
    class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
        private val devices = ArrayList<BluetoothDevice>()
        private val rssiMap = HashMap<String, Int>()

        @SuppressLint("MissingPermission")
        fun addDevice(device: BluetoothDevice, rssi: Int) {
            if (!devices.contains(device)) {
                devices.add(device)
                rssiMap[device.address] = rssi
                notifyItemInserted(devices.size - 1)
            } else {
                // 更新 RSSI
                rssiMap[device.address] = rssi
                val index = devices.indexOf(device)
                notifyItemChanged(index)
            }
        }

        fun clear() {
            devices.clear()
            rssiMap.clear()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("MissingPermission")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val device = devices[position]
            val rssi = rssiMap[device.address]
            
            val name = device.name ?: "Unknown Device"
            holder.text1.text = "$name ($rssi dBm)"
            holder.text2.text = device.address
        }

        override fun getItemCount() = devices.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text1: TextView = view.findViewById(android.R.id.text1)
            val text2: TextView = view.findViewById(android.R.id.text2)
        }
    }
}
