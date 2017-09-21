package com.guess.celebrities;

import java.util.List;

import com.guess.celebrities.R;
import com.guess.db.Tag;
import com.guess.db.Util;
import com.guess.newpuzzle.GameActivity;
import com.guess.newpuzzle.data.Contant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Activity mContext;
    private List<Tag> list;
    private Typeface buttonTypeface;
    private int type;
    
    public ImageAdapter(Activity c, List<Tag> list, int type) {
        this.mContext = c;
        this.list = list;
        this.type = type;
        AssetManager assets = c.getAssets();
		buttonTypeface = Util.getTypeFaceByName(
				Util.Fonts.CHERY_LINEY, assets);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
    	ViewHolder holder;
    	
    	if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.button_layout, null);
			int but_width = (int) mContext.getResources().getDimension(R.dimen.column_but_width);
			int but_height = (int) mContext.getResources().getDimension(R.dimen.column_but_height);
			view.setLayoutParams(new GridView.LayoutParams(but_width, but_height)); 
			view.setPadding(1, 1, 1, 1);  
			    
			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
    	if ((list == null) || ((position + 1) > list.size()))
			return view;
    	holder.button = (Button) view.findViewById(R.id.button_play);
		holder.imgView = (ImageView) view.findViewById(R.id.image_star);
		holder.button.setTypeface(buttonTypeface);
        holder.button.setText(""+(position+1));
        
        final Tag tag = list.get(position);
        int last_pos= position-1;
        Tag last_tag = null;
        if(last_pos >= 0){
           last_tag = list.get(last_pos);
        }
        holder.button.setTextColor(mContext.getResources().getColor(R.color.color_black));
        if(tag.getStatus() != Tag.GAME_STAR_0 || position == 0)
        {
        	holder.button.setBackgroundResource(R.drawable.game_but_style);
        	holder.button.setEnabled(true);        	
        }else if(last_tag != null && last_tag.getStatus() != Tag.GAME_STAR_0){
        	holder.button.setBackgroundResource(R.drawable.game_but_style);
        	holder.button.setEnabled(true);
   		}else{
   			holder.button.setBackgroundResource(R.drawable.lock_button_style);
        	holder.button.setEnabled(false);
        	holder.button.setTextColor(mContext.getResources().getColor(R.color.color_whith));
        }
        
        
        if(tag.getStatus() == Tag.GAME_STAR_0)        	
        {
        	holder.imgView.setBackgroundResource(R.drawable.star_1);
        }else if(tag.getStatus() == Tag.GAME_STAR_1){
        	holder.imgView.setBackgroundResource(R.drawable.star_01);
        }else if(tag.getStatus() == Tag.GAME_STAR_2){
        	holder.imgView.setBackgroundResource(R.drawable.star_2);
        }else if(tag.getStatus() == Tag.GAME_STAR_3){
        	holder.imgView.setBackgroundResource(R.drawable.star_3);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	 mContext.finish();
            	 if(tag.getStatus() == Tag.GAME_STAR_0){
            		Contant.GameType = 1;
            		Contant.hasNum=false;
         			Intent intent = new Intent();
         			intent.putExtra("id", tag.getId());
         			intent.putExtra("type", type);
         			intent.setClass(mContext, GameActivity.class);
         			mContext.startActivity(intent);
            //		Intent intentScore = new Intent(mContext,
            //     			PuzzleActivity.class);            	
            //     	intentScore.putExtra("id", tag.getId());
            //     	intentScore.putExtra("type", type);
            //     	mContext.startActivity(intentScore);
            	 }else if(tag.getStatus() == Tag.GAME_STAR_1 || tag.getStatus() == Tag.GAME_STAR_2){
            		Intent intentScore = new Intent(mContext,
                  			GameScreen.class);            	
                  	intentScore.putExtra("id", tag.getId());
                  	intentScore.putExtra("type", type);
                  	mContext.startActivity(intentScore);
            	 }else if(tag.getStatus() == Tag.GAME_STAR_3){
            		 Intent intentScore = new Intent(mContext,
            				 FinishGame.class);            	
                   	intentScore.putExtra("id", tag.getId());
                   	intentScore.putExtra("type", type);
                   	mContext.startActivity(intentScore); 
            	 }
            }
        });
        
        return view;
    }

  
    public class ViewHolder {
		public Button button;
		private ImageView imgView;

	}

}