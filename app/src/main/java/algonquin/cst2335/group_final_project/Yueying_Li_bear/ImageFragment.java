package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import algonquin.cst2335.group_final_project.R;

/**
 * This class maintains the fragment of the list of image views
 */
public class ImageFragment extends Fragment {
    private static final String ARG_IMAGE_URL = "imageUrl";

    /**
     * constructor with no argument
     */
    public ImageFragment() {
        // Required empty public constructor
    }

    /**
     * another constructor with a parameter
     * @param imageUrl the URL of an image
     * @return fragment the fragment of image list
     */
    public static ImageFragment newInstance(String imageUrl) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * creates the view of the image view
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view the view of the image list
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);

        if (getArguments() != null) {
            String imageUrl = getArguments().getString(ARG_IMAGE_URL);
            Picasso.get().load(imageUrl).into(imageView);
        }

        return view;
    }
}
