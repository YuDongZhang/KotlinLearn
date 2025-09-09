package com.example.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.databinding.ItemPersonBinding;
import com.example.mvvm.model.Person;

public class PersonAdapter extends ListAdapter<Person, PersonAdapter.PersonViewHolder> {

    private OnItemClickListener listener;

    public PersonAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                   oldItem.getAge() == newItem.getAge() &&
                   oldItem.getGender().equals(newItem.getGender()) &&
                   oldItem.getJob().equals(newItem.getJob());
        }
    };

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPersonBinding binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PersonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person currentPerson = getItem(position);
        holder.bind(currentPerson);
    }

    public Person getPersonAt(int position) {
        return getItem(position);
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        private ItemPersonBinding binding;

        public PersonViewHolder(@NonNull ItemPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        public void bind(Person person) {
            binding.setPerson(person);
            binding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
