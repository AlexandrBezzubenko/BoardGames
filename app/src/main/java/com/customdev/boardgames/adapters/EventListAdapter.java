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
import com.customdev.boardgames.models.Event;
import com.customdev.boardgames.models.Location;

import java.util.ArrayList;
import java.util.Calendar;


public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private Context mContext;
    private ArrayList<Event> mEventList;
    private OnEventButtonClickListener mClickListener;

    public EventListAdapter(Context context, ArrayList<Event> events, OnEventButtonClickListener listener) {
        mContext = context;
        mEventList = events;
        mClickListener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event, parent, false);
        EventViewHolder holder = new EventViewHolder(view, mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
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

        final int maxPlayers = event.getMaxPlayersCount();
        final int needPlayers = event.getNeedPlayersCount();
        holder.mEventPlayers.setText("Need payers: " + needPlayers + " Max players: " + maxPlayers);

        holder.mEventCreator.setText("Creator");

        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
        int mPosition;
        OnEventButtonClickListener mButtonClickListener;

        public EventViewHolder(View itemView, OnEventButtonClickListener listener) {
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
            mButtonClickListener.OnButtonClick(v, mPosition);
        }
    }

    public interface OnEventButtonClickListener {
        void OnButtonClick(View v, int position);
    }
}
