package soares.pedro.galeriapblica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class GridViewFragment extends Fragment {

    List<String> photos = new ArrayList<>();

    GridAdapter gridAdapter;

    private MainViewModel mViewModel;
    private View view;

    public GridViewFragment() {
    }
    public static GridViewFragment newInstance() {
        return new GridViewFragment();
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        GridAdapter gridAdapter = new GridAdapter(new ImageDataComparator());
        LiveData<PagingData<ImageData>> liveData = mViewModel.getPageLv();
        liveData.observe(getViewLifecycleOwner(), new Observer<PagingData<ImageData>>() {
            public void onChanged(PagingData<ImageData>objectPagingData) {
                gridAdapter.submitData(getViewLifecycleOwner().getLifecycle(),objectPagingData);
            }
        });
        RecyclerView rvGallery = (RecyclerView)view.findViewById(R.id.rvGrid);
        rvGallery.setAdapter(gridAdapter);
        float w = getResources().getDimension(R.dimen.im_width);
        int numberOfColumns = Util.calculateNoOfColumns(getContext(), w);
        rvGallery.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grid_view, container, false);
        return view;
    }
}