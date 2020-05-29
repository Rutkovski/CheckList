package com.rutkovski.checklist.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rutkovski.checklist.R;
import com.rutkovski.checklist.data.CheckPointTemplate;
import com.rutkovski.checklist.data.UserCheckPoint;

import java.util.ArrayList;
import java.util.List;


public class CheckPointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CheckPointTemplate> checkPointTemplates;
    private OnOptionSelected onOptionSelected;
    private int status;


    public CheckPointsAdapter(int status) {
        checkPointTemplates = new ArrayList<>();
        this.status = status;
    }

    public CheckPointsAdapter() {
        checkPointTemplates = new ArrayList<>();
        this.status = 3;
    }

    public void setOnOptionSelected(OnOptionSelected onOptionSelected) {
        this.onOptionSelected = onOptionSelected;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (status) {
            case (1):
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkpoint_item_write, parent, false);
                return new CheckPointsWriteViewHolder(view);

            case (2):
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkpoint_item_read, parent, false);
                return new CheckPointsReadViewHolder(view);

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkpoint_item_template, parent, false);
                return new CheckPointsTemplateViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CheckPointTemplate checkPointTemplate = checkPointTemplates.get(position);

        if (status == 1) {
            CheckPointsWriteViewHolder writeHolder = (CheckPointsWriteViewHolder) holder;
            writeHolder.textViewCheckPoint.setText(checkPointTemplate.getDescription());
            writeHolder.setIsRecyclable(false);

            int result = checkPointTemplate.getResult();
            if (result != 0) {
                switch (result) {
                    case (1):
                        writeHolder.radioButtonLike.setChecked(true);
                        break;
                    case (2):
                        writeHolder.radioButtonDislike.setChecked(true);
                        break;
                    case (3):
                        writeHolder.radioButtonSkip.setChecked(true);
                        break;
                }
            }

            UserCheckPoint userCheckPoint = (UserCheckPoint) checkPointTemplate;
            if (userCheckPoint.getNote() != null && userCheckPoint.getNote().length() > 0) {
                writeHolder.imageViewAddNote.setImageResource(R.drawable.note_text);
            }

        } else if (status == 2) {
            CheckPointsReadViewHolder readViewHolder = (CheckPointsReadViewHolder) holder;
            readViewHolder.textViewCheckPointRead.setText(checkPointTemplate.getDescription());

            int result = checkPointTemplate.getResult();
            switch (result) {
                case (1):
                    readViewHolder.textViewResult.setText("Да");
                    break;
                case (2):
                    readViewHolder.textViewResult.setText("Нет");
                    readViewHolder.textViewResult.setTextColor(Color.RED);
                    break;
                case (3):
                    readViewHolder.textViewResult.setText("Не актуально");
                    readViewHolder.textViewResult.setTextColor(Color.GRAY);
                    break;
            }
            UserCheckPoint userCheckPoint = (UserCheckPoint) checkPointTemplate;
            if (userCheckPoint.getNote() != null && userCheckPoint.getNote().length() > 0) {
                readViewHolder.imageViewNote.setImageResource(R.drawable.note_text);
            }


        } else {
            CheckPointsTemplateViewHolder templateViewHolder = (CheckPointsTemplateViewHolder) holder;
            templateViewHolder.textViewCheckPointTemplate.setText(checkPointTemplate.getDescription());
        }


    }

    @Override
    public int getItemCount() {
        return checkPointTemplates.size();
    }

    public List<CheckPointTemplate> getCheckPointTemplates() {
        return checkPointTemplates;
    }

    public void setCheckPoint(List<CheckPointTemplate> checkPointTemplates) {
        this.checkPointTemplates = checkPointTemplates;
        notifyDataSetChanged();
    }

    public void setUserCheckPoint(List<UserCheckPoint> userCheckPoints) {
        List<CheckPointTemplate> arrayList = new ArrayList<>();
        arrayList.addAll(userCheckPoints);
        this.checkPointTemplates = arrayList;
        notifyDataSetChanged();
    }

    public void clear() {
        this.checkPointTemplates.clear();
    }


    public interface OnOptionSelected {
        void onOptionSelected(int adapterPosition, int checkedButtonId, RadioGroup radioGroup);

        void onNoteClick(int adapterPosition);
    }

    public class CheckPointsWriteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCheckPoint;
        RadioGroup radioGroup;
        RadioButton radioButtonLike;
        RadioButton radioButtonSkip;
        RadioButton radioButtonDislike;
        ImageView imageViewAddNote;

        public CheckPointsWriteViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewCheckPoint = itemView.findViewById(R.id.textViewCheckPointWrite);
            radioGroup = itemView.findViewById(R.id.radioGroupChoice);
            radioButtonLike = itemView.findViewById(R.id.radioButtonLike);
            radioButtonDislike = itemView.findViewById(R.id.radioButtonDislike);
            radioButtonSkip = itemView.findViewById(R.id.radioButtonSkip);
            imageViewAddNote = itemView.findViewById(R.id.imageViewNoteAdd);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (onOptionSelected != null) {
                        onOptionSelected.onOptionSelected(getAdapterPosition(), i, radioGroup);
                    }
                }
            });

            imageViewAddNote    .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onOptionSelected != null) {
                        onOptionSelected.onNoteClick(getAdapterPosition());
                    }
                }
            });

        }
    }

    public class CheckPointsReadViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCheckPointRead;
        TextView textViewResult;
        ImageView imageViewNote;

        public CheckPointsReadViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewCheckPointRead = itemView.findViewById(R.id.textViewCheckPointRead);
            textViewResult = itemView.findViewById(R.id.textViewResult);
            imageViewNote = itemView.findViewById(R.id.imageViewNoteRead);
            imageViewNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onOptionSelected != null) {
                        onOptionSelected.onNoteClick(getAdapterPosition());
                    }
                }
            });

        }


    }

    public class CheckPointsTemplateViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCheckPointTemplate;

        public CheckPointsTemplateViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewCheckPointTemplate = itemView.findViewById(R.id.textViewCheckPointTemplate);
        }
    }


}
