package com.customdev.boardgames.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.customdev.boardgames.R;
import com.customdev.boardgames.interfaces.OnEventViewButtonClickListener;
import com.customdev.boardgames.models.Event;
import com.customdev.boardgames.models.Location;
import com.customdev.boardgames.models.User;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;


public class EventListParallaxRecyclerAdapter extends ParallaxRecyclerAdapter<Event> {

    private Context mContext;
    private ArrayList<Event> mEventList;
    private OnEventViewButtonClickListener mClickListener;

    public EventListParallaxRecyclerAdapter(Context context, ArrayList<Event> events, OnEventViewButtonClickListener listener) {
        super(events);
        mContext = context;
        mEventList = events;
        mClickListener = listener;
    }

    @Override
    public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<Event> parallaxRecyclerAdapter, int position) {

        EventViewHolder holder = (EventViewHolder) viewHolder;

        Event event = mEventList.get(position);
        Resources res = mContext.getResources();

        final String str = event.getGame().getLogoTag();
        final int resId = res.getIdentifier(str, "drawable", mContext.getPackageName());
        holder.mEventLogoImg.setImageResource(resId);

        final String type = res.getStringArray(R.array.types)[event.getType()];
        holder.mEventType.setText(type);

        final String title = event.getGame().getName();
        holder.mEventGameTitle.setText(title);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(event.getStartTime());
        final String time = calendar.getTime().toString();
        holder.mEventTime.setText(time);

        final Location location = event.getLocation();
        final String city = res.getStringArray(R.array.cities)[location.getCity()];
        final String club = res.getStringArray(R.array.clubs)[location.getClub()];
        holder.mEventLocation.setText(city + " " + club);

        final int needPlayers = event.getNeedPlayersCount();
        final int maxPlayers = event.getMaxPlayersCount();
        final String playersCountText = mContext.getString(R.string.event_list_item_players_count, needPlayers, maxPlayers);
        holder.mEventPlayers.setText(playersCountText);

        final User creator = event.getCreator();
        String creatorNickname = "";
        if (creator != null)
            creatorNickname = creator.getNickname();
        final String creatorText = mContext.getString(R.string.event_list_item_creator, creatorNickname);
        holder.mEventCreator.setText(creatorText);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<Event> parallaxRecyclerAdapter, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_event, viewGroup, false);
        return new EventViewHolder(view, mClickListener);
    }

    @Override
    public int getItemCountImpl(ParallaxRecyclerAdapter<Event> parallaxRecyclerAdapter) {
        return mEventList.size();
    }


    private static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView mCardView;
        ImageView mEventLogoImg;
        TextView mEventType;
        TextView mEventGameTitle;
        TextView mEventTime;
        TextView mEventLocation;
        TextView mEventPlayers;
        TextView mEventCreator;
        ImageButton mEventEditBtn;
        ImageButton mEventDeleteBtn;
        ImageButton mEventConfirmBtn;
        ImageButton mEventDenyBtn;
        ImageButton mEventInviteBtn;
        OnEventViewButtonClickListener mButtonClickListener;

        private EventViewHolder(View itemView, OnEventViewButtonClickListener listener) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.card_view_event);
            mEventLogoImg = (ImageView) itemView.findViewById(R.id.image_event_logo);
            mEventType = (TextView) itemView.findViewById(R.id.text_event_type);
            mEventGameTitle = (TextView) itemView.findViewById(R.id.text_event_name);
            mEventTime = (TextView) itemView.findViewById(R.id.text_event_time);
            mEventLocation = (TextView) itemView.findViewById(R.id.text_event_location);
            mEventPlayers = (TextView) itemView.findViewById(R.id.text_event_players);
            mEventCreator = (TextView) itemView.findViewById(R.id.text_event_creator);
            mEventEditBtn = (ImageButton) itemView.findViewById(R.id.button_event_edit);
            mEventEditBtn.setOnClickListener(this);
            mEventDeleteBtn = (ImageButton) itemView.findViewById(R.id.button_event_delete);
            mEventDeleteBtn.setOnClickListener(this);
            mEventConfirmBtn = (ImageButton) itemView.findViewById(R.id.button_event_confirm);
            mEventConfirmBtn.setOnClickListener(this);
            mEventDenyBtn = (ImageButton) itemView.findViewById(R.id.button_event_deny);
            mEventDenyBtn.setOnClickListener(this);
            mEventInviteBtn = (ImageButton) itemView.findViewById(R.id.button_event_invite);
            mEventInviteBtn.setOnClickListener(this);
            mButtonClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            mButtonClickListener.OnButtonClick(v, getAdapterPosition());
        }
    }
}
