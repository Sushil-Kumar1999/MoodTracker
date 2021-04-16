package com.example.moodtracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryHolder> {

    private List<Entry> entries = new ArrayList();
    private Map<Integer, Boolean> entryStates;

    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item, parent, false);
        return new EntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryHolder holder, int position) {
        Entry currentEntry = entries.get(position);
        bindData(holder, currentEntry);

        holder.itemView.setOnClickListener(v -> {
            int entryId = currentEntry.getId();
            boolean expanded = this.entryStates.get(entryId);
            this.entryStates.put(entryId, !expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        this.entryStates = getEntryStates(entries);
        notifyDataSetChanged();
    }

    public Entry getEntryAt(int position) {
        return this.entries.get(position);
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
        }
    }

    private int getEmojiResourceId(Mood mood) {
        int resId = R.drawable.very_happy_face; // mood - cheerful

        switch (mood) {
            case Happy:
                resId = R.drawable.happy_face;
                break;
            case Meh:
                resId = R.drawable.neutral_face;
                break;
            case Sad:
                resId = R.drawable.very_sad_face;
                break;
            case Angry:
                resId = R.drawable.angry_face;
                break;
        }

        return resId;
    }

    private int getColor(Mood mood) {
        int colorInt = Color.parseColor("#FFFF00"); //yellow for cheerful mood

        switch (mood) {
            case Happy:
                colorInt = Color.parseColor("#98FB98");
                break;
            case Meh:
                colorInt = Color.parseColor("#6495ED");
                break;
            case Sad:
                colorInt = Color.parseColor("#A9A9A9");
                break;
            case Angry:
                colorInt = Color.parseColor("#DC143C");
                break;
        }

        return colorInt;
    }

    private Map<Integer, Boolean> getEntryStates(List<Entry> entries) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (Entry entry : entries) {
            map.put(entry.getId(), false);
        }
        return map;
    }

    private void bindData(EntryHolder holder, Entry currentEntry) {
        boolean isExpanded = this.entryStates.get(currentEntry.getId());
        Mood currentMood = currentEntry.getMood();

        holder.textViewMood.setText(currentMood.toString());

        Date currentDate = currentEntry.getDate();
        String dateString = DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
                .format(currentDate);
        holder.textViewDateTime.setText(dateString);

        holder.imageViewMood.setImageResource(getEmojiResourceId(currentMood));
        holder.relativeLayout.setBackgroundColor(getColor(currentMood));

        holder.containerEntryDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.textViewSleepDuration.setText(String.format("%s hours", currentEntry.getSleepDuration()));
        holder.textViewHadBreakfastValue.setText(currentEntry.isHadBreakfast() ? "Yes" : "No");
        holder.textViewHadLunchValue.setText(currentEntry.isHadLunch() ? "Yes" : "No");
        holder.textViewHadDinnerValue.setText(currentEntry.isHadDinner() ? "Yes" : "No");
    }
}
