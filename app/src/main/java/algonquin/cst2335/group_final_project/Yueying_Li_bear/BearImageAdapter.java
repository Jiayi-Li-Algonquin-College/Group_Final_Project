package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

//import android.support.annotation.NonNull;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.group_final_project.R;

/**
 * This class maintains the bear image adapter of RecyclerView, ViewHolder,
 * and a method to manipulate the Fragment of details of bear image.
 */
public class BearImageAdapter extends RecyclerView.Adapter<BearImageAdapter.ViewHolder> {

    private List<BearImage> bearImages;
    private BearImageDAO bearImageDAO;

    /**
     * constructor with two parameters
     * @param bearImages the object of BearImage
     * @param bearImageDAO the object of BearImageDAO
     */
    public BearImageAdapter(List<BearImage> bearImages, BearImageDAO bearImageDAO) {
//        this.images = images;
        this.bearImages = bearImages;
        this.bearImageDAO = bearImageDAO;
    }

    /**
     * creates a new ViewHolder
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a new object of ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bear_image_adapter, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Manipulates the Fragment
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BearImage bearImage = bearImages.get(position);

//        Handler handler = new Handler();
//        final Runnable[] mRunnable = new Runnable[1];
//        final CountDownTimer[] mCountDownTimer = new CountDownTimer[1];

        GestureDetector gestureDetector = new GestureDetector(holder.itemView.getContext(), new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                // Double tap detected
//                return true;
//            }

            /**
             * Gestures the Down
             * @param e The down motion event.
             * @return a boolean true
             */
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                return false;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return false;
//            }



//        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

            /**
             * Manipulates the double click
             * @param e The down motion event of the first tap of the double-tap.
             * @return a boolean true
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
//                if (gestureDetector.onTouchEvent(motionEvent)) {
//            public void onClick(View view) {

//                if (mRunnable[0] != null) {
//                    handler.removeCallbacks(mRunnable[0]);
//                    mRunnable[0] = null;
//                }
//                if (mCountDownTimer[0] != null) {
//                    mCountDownTimer[0].cancel();
//                    mCountDownTimer[0] = null;
//                }

                // Double tap
                BearImageDetailFragment bearImageDetailFragment = BearImageDetailFragment.newInstance(String.valueOf(bearImage.getId()), bearImage.getUrl());
                ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, bearImageDetailFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
//                } else {
            }

//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return false;
//            }

            /**
             * Manipulates the single click
             * @param e The down motion event of the single-tap.
             * @return a boolean true
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //single tap
//                mRunnable[0] = new Runnable() {
//                mCountDownTimer[0] = new CountDownTimer(350, 350) {
//                    public void onTick(long millisUntilFinished) {
//                        // Nothing to do here
//                    }
//                    @Override
//                    public void run() {
//                    public void onFinish() {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Delete Confisrmation")
                        .setMessage("Do you want to delete this image?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Delete image from the list and database
                                Snackbar snackbar = Snackbar.make(holder.itemView, "Image will be deleted", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(holder.itemView.getContext(), "Image delete operation cancelled", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                snackbar.addCallback(new Snackbar.Callback() {

                                    /**
                                     * Manipulates the Snackbar of deleting images
                                     * @param snackbar The transient bottom bar which has been dismissed.
                                     * @param event The event which caused the dismissal. One of either: {@link
                                     *     #DISMISS_EVENT_SWIPE}, {@link #DISMISS_EVENT_ACTION}, {@link #DISMISS_EVENT_TIMEOUT},
                                     *     {@link #DISMISS_EVENT_MANUAL} or {@link #DISMISS_EVENT_CONSECUTIVE}.
                                     */
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                                            // Only remove the image when Snackbar is not dismissed by action
                                            int adapterPos = holder.getAdapterPosition();
                                            if (adapterPos != RecyclerView.NO_POSITION) {
                                                BearImage bearImageToDelete = bearImages.get(adapterPos);
                                                bearImages.remove(position);
                                                new Thread(() -> bearImageDAO.deleteBearImage(bearImage)).start();
                                                notifyDataSetChanged();
                                                Toast.makeText(holder.itemView.getContext(), "Image deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    /**
                                     * displays the text of "Image deleted"
                                     * @param snackbar The transient bottom bar which is now visible.
                                     */
                                    @Override
                                    public void onShown(Snackbar snackbar) {
                                    }
                                });
                                snackbar.show();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            /**
                             * maniputes the Toast text about "Image saved"
                             * @param dialog the dialog that received the click
                             * @param id the button that was clicked (ex.
                             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                             *              of the item clicked
                             */
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(holder.itemView.getContext(), "Image saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
//                    }
//                }.start();
//                handler.postDelayed(mRunnable[0], 350); // 350 is the time delay in ms

                return true;
            }
        });


//            @Override
//            public void onClick(View view) {
//                ImageFragment imageFragment = ImageFragment.newInstance(bearImage.url);
//                ((FragmentActivity) view.getContext()).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, imageFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

        holder.itemView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        Picasso.get().load(bearImages.get(position).url).into(holder.imageView);
    }

    /**
     * gets item count
     * @return the size of a bear image
     */
    @Override
    public int getItemCount() {
        return bearImages.size();
    }


    /**
     * updates the list of iamges
     * @param newImages
     */
    public void updateImages(List<BearImage> newImages) {
//        this.images = newImages;
        this.bearImages.clear();
        this.bearImages.addAll(newImages);
        notifyDataSetChanged();
    }

    /**
     * gets the position of bear image
     * @param position
     * @return
     */
    public BearImage getBearImageAtPosition(int position) {
        return bearImages.get(position);
    }

    /**
     * This inner class maintains the image ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.bearListImageView);
        }
    }
}

//new *
//public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
//    private final List<Image> images;
//    private OnImageClickListener listener;
//
//    public ImageListAdapter(List<Image> images) {
//        this.images = images;
//    }
//
//    public interface OnImageClickListener {
//        void onImageClick(Image image);
//    }
//
//    public void setOnImageClickListener(OnImageClickListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ActivityBearImageAdapterBinding binding = ActivityBearImageAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        return new ImageViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        Image currentImage = images.get(position);
//        holder.binding.bearListImageView.setImageBitmap(currentImage.getBitmap());
//        holder.binding.deleteButton.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onImageClick(currentImage);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return images.size();
//    }
//
//    class ImageViewHolder extends RecyclerView.ViewHolder {
//        private final RecyclerviewItemBinding binding;
//
//        public ImageViewHolder(@NnNull ActivityBearImageAdapterBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
//}
// new **
