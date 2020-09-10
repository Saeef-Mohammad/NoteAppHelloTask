package com.saeefmd.noteapphellotask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saeefmd.noteapphellotask.R;
import com.saeefmd.noteapphellotask.model.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;

    private Context mContext;

    public NoteAdapter(Context context) {
        super(DIFF_CALLBACK);

        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notes, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note currentNote = getItem(position);

        holder.tvTitle.setText(currentNote.getTitle());
        holder.tvDescription.setText(currentNote.getDescription());
        holder.tvDate.setText(currentNote.getDate());
        holder.tvMonth.setText(currentNote.getMonth());

        if (position % 4 == 0) {
            holder.colorBorder.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorOrange));
        } else if (position % 4 == 1) {
            holder.colorBorder.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBlue));
        } else if (position % 4 == 2) {
            holder.colorBorder.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorRed));
        } else if (position % 4 == 3) {
            holder.colorBorder.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorGreen));
        }

        if (currentNote.getPriority() == 1) {
            holder.ivStar.setVisibility(View.VISIBLE);
        } else {
            holder.ivStar.setVisibility(View.INVISIBLE);
        }
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvMonth;

        private ImageView ivStar;

        private View colorBorder;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title_rv);
            tvDescription = itemView.findViewById(R.id.tv_description_rv);
            tvDate = itemView.findViewById(R.id.tv_date_rv);
            tvMonth = itemView.findViewById(R.id.tv_month_rv);

            ivStar = itemView.findViewById(R.id.iv_star);

            colorBorder = itemView.findViewById(R.id.view_color_border);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {

        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
