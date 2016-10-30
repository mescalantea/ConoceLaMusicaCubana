package com.github.mescalantea.conocelamusicacubana.view;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mescalantea.conocelamusicacubana.R;
import com.github.mescalantea.conocelamusicacubana.model.Score;
import java.util.ArrayList;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder>{

    private ArrayList<Score> list;

    public ScoreListAdapter(ArrayList<Score> list) {
        this.list = list;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_score, parent, false);
        ScoreViewHolder vh = new ScoreViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        Score item = list.get(position);
        holder.bindScore(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ScoreViewHolder extends RecyclerView.ViewHolder {

        private TextView player;
        private TextView score;

        public ScoreViewHolder(View itemView) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(),"cubano.ttf");

            player = (TextView)itemView.findViewById(R.id.playerNameTextView);
            score = (TextView)itemView.findViewById(R.id.scoreTextView);

            player.setTypeface(typeface);
            score.setTypeface(typeface);
        }

        public void bindScore(Score s) {
            player.setText(s.getPlayer());
            int percent = s.getPercent();
            score.setText(percent+"%");

            if(percent < 50){
                score.setTextColor(score.getContext().getResources().getColor(R.color.colorPrimary));
            }
            else{
                if(percent < 80){
                    score.setTextColor(score.getContext().getResources().getColor(R.color.colorMedium));
                }
                else{
                    score.setTextColor(score.getContext().getResources().getColor(R.color.colorSuccess));
                }
            }
        }
    }
}
