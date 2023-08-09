package algonquin.cst2335.group_final_project.Yueying_Li_bear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.group_final_project.R;

/**
 * This class maintains the Fragment of details of image bear
 */
public class BearImageDetailFragment extends Fragment {
    private String id, url;

    /**
     * The constructor with no argument
     */
    public BearImageDetailFragment() {
        // Required empty public constructor
    }

    /**
     * creates a new instance of bear image
     * @param id the id of bear image
     * @param url the url of bear image
     * @return fragment the fragment of details of an image
     */
    public static BearImageDetailFragment newInstance(String id, String url) {
        BearImageDetailFragment fragment = new BearImageDetailFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * creates an instance of image bear
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            url = getArguments().getString("url");
        }
    }

    /**
     * creates a view of layout of details fragment of images
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view the view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bear_image_detail, container, false);

        // assuming you have TextViews with these ids in fragment_bear_image_detail.xml
        TextView idTextView = view.findViewById(R.id.image_id);
        TextView urlTextView = view.findViewById(R.id.image_url);

        idTextView.setText(id);
        urlTextView.setText(url);

        return view;
    }
}
