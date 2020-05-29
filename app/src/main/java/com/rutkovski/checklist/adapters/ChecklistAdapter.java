package com.rutkovski.checklist.adapters;

import android.graphics.Paint;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.rutkovski.checklist.R;
import com.rutkovski.checklist.data.CheckListTemplate;
import com.rutkovski.checklist.data.UserCheckList;

import java.util.ArrayList;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder> {








    private List<CheckListTemplate> checkListTemplates;
    private OnCheckListClickListener onCheckListClickListener;
    private boolean isTemplate;



    public ChecklistAdapter(boolean isTemplate) {
        checkListTemplates = new ArrayList<>();
        this.isTemplate = isTemplate;
    }

    public interface OnCheckListClickListener{
        void onCheckListClick(int position);
        void onCheckLongClick(MenuItem item, int position);
    }

    @NonNull
    @Override
    public ChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
       if (!isTemplate) {
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);
       }
       else {
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);
       }
       return new ChecklistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistViewHolder holder, final int position) {
            /*holder.setIsRecyclable(false);*/

        CheckListTemplate checkListTemplate = checkListTemplates.get(position);
        holder.textViewTitleOfCheckList.setText(checkListTemplate.getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view,Gravity.RIGHT);
                if(!isTemplate) {
                    popupMenu.inflate(R.menu.context_task_menu);
                }
                else {
                    popupMenu.inflate(R.menu.context_template_menu);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (onCheckListClickListener != null) {
                            onCheckListClickListener.onCheckLongClick(menuItem, position);
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });


        if (!isTemplate) {
            UserCheckList userCheckList = (UserCheckList) checkListTemplate;
            if (userCheckList.getStatus() == 2) {
                holder.textViewTitleOfCheckList.setPaintFlags(holder.textViewTitleOfCheckList.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
        else {
            holder.textViewTitleOfCheckList.setPaintFlags(0);
        }

    }

    @Override
    public int getItemCount() {
        return checkListTemplates.size();
    }


    public class ChecklistViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitleOfCheckList;

        public ChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitleOfCheckList = itemView.findViewById(R.id.textViewTitleOfCheckList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCheckListClickListener!=null){
                        onCheckListClickListener.onCheckListClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    // На будущее
    /*public class UserChecklistViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitleOfCheckList;

        public UserChecklistViewHolder (@NonNull View itemView) {
            super(itemView);
            textViewTitleOfCheckList = itemView.findViewById(R.id.textViewTitleOfCheckList);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCheckListClickListener!=null){
                        onCheckListClickListener.onCheckListClick(getAdapterPosition());
                    }
                }
            });
        }
    }*/







    public List<CheckListTemplate> getCheckListTemplates() {
        return checkListTemplates;
    }

    public void setCheckList (List<CheckListTemplate> checkListTemplates) {
        this.checkListTemplates = checkListTemplates;
        notifyDataSetChanged();
    }

    public void setUserCheckList (List<UserCheckList> userCheckList) {
        List<CheckListTemplate> checkListTemplate = new ArrayList<>();
        checkListTemplate.addAll(userCheckList);
        this.checkListTemplates = checkListTemplate;
        notifyDataSetChanged();
    }

    public void setOnCheckListClickListener(OnCheckListClickListener onCheckListClickListener) {
        this.onCheckListClickListener = onCheckListClickListener;
    }
    public void clear(){
        checkListTemplates.clear();

    }



}
