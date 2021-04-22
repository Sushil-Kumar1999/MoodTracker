package com.example.moodtracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodtracker.R;
import com.example.moodtracker.data.Entry;
import com.example.moodtracker.data.Mood;
import com.example.moodtracker.utils.Utilities;

import java.text.DateFormat;
import java.util.Date;

public class EntryAdapter extends ListAdapter<Entry, EntryAdapter.EntryHolder> {

    public EntryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Entry> DIFF_CALLBACK = new DiffUtil.ItemCallback<Entry>() {
        @Override
        public boolean areItemsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
            return oldItem.getSleepDuration() == newItem.getSleepDuration() &&
                    oldItem.getMood().equals(newItem.getMood()) &&
                    oldItem.isHadBreakfast() == newItem.isHadBreakfast() &&
                    oldItem.isHadLunch() == newItem.isHadLunch() &&
                    oldItem.isHadDinner() == newItem.isHadDinner() &&
                    oldItem.getNote().equals(newItem.getNote());
        }
    };

    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item, parent, false);
        return new EntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryHolder holder, int position) {
        Entry currentEntry = getItem(position);
        bindData(holder, currentEntry);
    }

    public Entry getEntryAt(int position) {
        return getItem(position);
    }

    class EntryHolder extends RecyclerView.ViewHolder {
        private TextView textViewMood;
        private TextView textViewDateTime;
        private ImageView imageViewMood;
        private RelativeLayout relativeLayout;
        private RelativeLayout containerEntryDetails;
        private TextView textViewSleepDuration;
        private TextView textViewHadBreakfastValue;
        private TextView textViewHadLunchValue;
        private TextView textViewHadDinnerValue;
        private TextView textViewNoteValue;
        private boolean isExpanded;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMood = itemView.findViewById(R.id.image_view_mood);
            textViewMood = itemView.findViewById(R.id.text_view_mood);
            textViewDateTime = itemView.findViewById(R.id.text_view_dateTime);
            relativeLayout = itemView.findViewById(R.id.relative_layout_entry_item);
            containerEntryDetails = itemView.findViewById(R.id.container_entry_details);
            textViewSleepDuration = itemView.findViewById(R.id.text_view_sleep_duration);
            textViewHadBreakfastValue = itemView.findViewById(R.id.had_breakfast_value);
            textViewHadLunchValue = itemView.findViewById(R.id.had_lunch_value);
            textViewHadDinnerValue = itemView.findViewById(R.id.had_dinner_value);
            textViewNoteValue = itemView.findViewById(R.id.text_view_note_value);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    isExpanded = !isExpanded;
                    notifyItemChanged(position);
                }
            });
        }
    }

    private void bindData(EntryHolder holder, Entry currentEntry) {
        Mood currentMood = currentEntry.getMood();
        holder.textViewMood.setText(currentMood.toString());

        Date currentDate = currentEntry.getDate();
        String dateString = DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                .format(currentDate);
        holder.textViewDateTime.setText(dateString);

        holder.imageViewMood.setImageResource(Utilities.getEmojiResourceId(currentMood));
        holder.relativeLayout.setBackgroundColor(Utilities.getColor(currentMood));

        holder.containerEntryDetails.setVisibility(holder.isExpanded ? View.VISIBLE : View.GONE);
        holder.textViewSleepDuration.setText(String.format("%s hours", currentEntry.getSleepDuration()));
        holder.textViewHadBreakfastValue.setText(currentEntry.isHadBreakfast() ? "Yes" : "No");
        holder.textViewHadLunchValue.setText(currentEntry.isHadLunch() ? "Yes" : "No");
        holder.textViewHadDinnerValue.setText(currentEntry.isHadDinner() ? "Yes" : "No");

        if (currentEntry.getNote().isEmpty()) {
            holder.textViewNoteValue.setText("No note available");
        }
        else {
            holder.textViewNoteValue.setText(currentEntry.getNote());
        }
    }
}
