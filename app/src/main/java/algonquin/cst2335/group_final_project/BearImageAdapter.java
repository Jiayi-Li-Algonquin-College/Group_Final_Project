//package algonquin.cst2335.group_final_project;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.media.Image;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//import algonquin.cst2335.group_final_project.databinding.ActivityBearImageAdapterBinding;
//
//public class BearImageAdapter extends RecyclerView.Adapter<BearImageAdapter.ViewHolder> {
//
//    private final List<BearImage> images;
//
//    public BearImageAdapter(List<BearImage> images) {
//        this.images = images;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bear_image_adapter, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Picasso.get().load(images.get(position).url).into(holder.imageView);
//    }
//
//    @Override
//    public int getItemCount() {
//        return images.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//
//        ViewHolder(View view) {
//            super(view);
//            imageView = view.findViewById(R.id.bearListImageView);
//        }
//    }
//}
//
////new *
////public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
////    private final List<Image> images;
////    private OnImageClickListener listener;
////
////    public ImageListAdapter(List<Image> images) {
////        this.images = images;
////    }
////
////    public interface OnImageClickListener {
////        void onImageClick(Image image);
////    }
////
////    public void setOnImageClickListener(OnImageClickListener listener) {
////        this.listener = listener;
////    }
////
////    @NonNull
////    @Override
////    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        ActivityBearImageAdapterBinding binding = ActivityBearImageAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
////        return new ImageViewHolder(binding);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
////        Image currentImage = images.get(position);
////        holder.binding.bearListImageView.setImageBitmap(currentImage.getBitmap());
////        holder.binding.deleteButton.setOnClickListener(v -> {
////            if (listener != null) {
////                listener.onImageClick(currentImage);
////            }
////        });
////    }
////
////    @Override
////    public int getItemCount() {
////        return images.size();
////    }
////
////    class ImageViewHolder extends RecyclerView.ViewHolder {
////        private final RecyclerviewItemBinding binding;
////
////        public ImageViewHolder(@NnNull ActivityBearImageAdapterBinding binding) {
////            super(binding.getRoot());
////            this.binding = binding;
////        }
////    }
////}
//// new **