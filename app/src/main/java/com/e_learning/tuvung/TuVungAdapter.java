package com.e_learning.tuvung;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.e_learning.R;

import java.io.IOException;
import java.util.List;

public class TuVungAdapter extends ArrayAdapter<TuVung> {
    private Context context;
    private List<TuVung> items;

    public TuVungAdapter(Context context, List<TuVung> items) {
        super(context, R.layout.item_vocab, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_vocab, parent, false);
        }

        TuVung currentItem = items.get(position);

        TextView word = convertView.findViewById(R.id.word);
        TextView meaning = convertView.findViewById(R.id.meaning);
        TextView pop = convertView.findViewById(R.id.part_of_speech);
        TextView example = convertView.findViewById(R.id.example_sentence);
        ImageView imgWord = convertView.findViewById(R.id.imageWord);
        ImageButton btnAudio = convertView.findViewById(R.id.play_audio);
        SeekBar seekBar = convertView.findViewById(R.id.audio_length);

        word.setText(Html.fromHtml(String.format("<b>%s</b> %s", "Từ vựng:", currentItem.getTu())));
        meaning.setText(Html.fromHtml(String.format("<b>%s</b> %s", "Nghĩa:", currentItem.getDichnghia())));
        pop.setText(Html.fromHtml(String.format("<b>%s</b> %s", "Loại từ:", currentItem.getLoaitu())));
        example.setText(Html.fromHtml(String.format("<b>%s</b> %s", "Ví dụ:", currentItem.getViDu())));

        Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getAnh(), 0, currentItem.getAnh().length);
        imgWord.setImageBitmap(bitmap);

        MediaPlayer mediaPlayer = new MediaPlayer();
        Handler handler = new Handler();

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer.reset();
                    AssetFileDescriptor afd = context.getAssets().openFd(currentItem.getAudio());
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            seekBar.setMax(mp.getDuration());
                            updateSeekBar(mp, seekBar, handler);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }

    private void updateSeekBar(MediaPlayer mediaPlayer, SeekBar seekBar, Handler handler) {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar(mediaPlayer, seekBar, handler);
                }
            };
            handler.postDelayed(runnable, 0);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar.setProgress(0);
            }
        });
    }
}