package com.github.ayvazj.rokontrol.sample;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.github.ayvazj.rokontrol.RokuAppInfo;
import com.github.ayvazj.rokontrol.RokuButton;
import com.github.ayvazj.rokontrol.RokuExControlClient;
import com.github.ayvazj.rokontrol.RokuKey;
import com.github.ayvazj.rokontrol.RokuSearchResult;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RokuRemoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RokuRemoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RokuRemoteFragment extends Fragment implements RokuButton.RokuButtonActionListener {
    private static final String ARG_RSR = "RokuRemoteFragment.RokuSearchResult";
    private OnFragmentInteractionListener mListener;
    private RokuSearchResult mRsr;

    RokuButton upBtn;
    RokuButton rightBtn;
    RokuButton downBtn;
    RokuButton leftBtn;
    RokuButton homeBtn;
    RokuButton selectBtn;
    RokuButton backBtn;
    RokuButton infoBtn;
    RokuButton rwdBtn;
    RokuButton playBtn;
    RokuButton fwdBtn;
    RokuButton replayBtn;

    private RokuExControlClient rokuClient;
    private GridView gridView;
    private RokuAppImageAdapter adapter;
    private ArrayList<RokuAppInfo> rokuAppInfoList;


    public static RokuRemoteFragment newInstance(RokuSearchResult rsr) {
        RokuRemoteFragment fragment = new RokuRemoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RSR, rsr);
        fragment.setArguments(args);
        return fragment;
    }

    public RokuRemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRsr = getArguments().getParcelable(ARG_RSR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roku_remote, container, false);

        gridView = (GridView) view.findViewById(R.id.gridView);
        rokuAppInfoList = new ArrayList<RokuAppInfo>();
        adapter = new RokuAppImageAdapter(getActivity(), rokuAppInfoList);
        gridView.setAdapter(adapter);

        upBtn = (RokuButton) view.findViewById(R.id.upBtn);
        rightBtn = (RokuButton) view.findViewById(R.id.rightBtn);
        downBtn = (RokuButton) view.findViewById(R.id.downBtn);
        leftBtn = (RokuButton) view.findViewById(R.id.leftBtn);
        homeBtn = (RokuButton) view.findViewById(R.id.homeBtn);
        selectBtn = (RokuButton) view.findViewById(R.id.selectBtn);
        fwdBtn = (RokuButton) view.findViewById(R.id.fwdBtn);
        playBtn = (RokuButton) view.findViewById(R.id.playBtn);
        infoBtn = (RokuButton) view.findViewById(R.id.infoBtn);
        rwdBtn = (RokuButton) view.findViewById(R.id.rwdBtn);
        replayBtn = (RokuButton) view.findViewById(R.id.replayBtn);

        upBtn.setRokuButtonActionListener(this);
        rightBtn.setRokuButtonActionListener(this);
        downBtn.setRokuButtonActionListener(this);
        leftBtn.setRokuButtonActionListener(this);
        homeBtn.setRokuButtonActionListener(this);
        selectBtn.setRokuButtonActionListener(this);
        fwdBtn.setRokuButtonActionListener(this);
        playBtn.setRokuButtonActionListener(this);
        infoBtn.setRokuButtonActionListener(this);
        rwdBtn.setRokuButtonActionListener(this);
        replayBtn.setRokuButtonActionListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.rokuClient = RokuExControlClient.connect(this.mRsr);
        this.rokuClient.queryApps(new RokuExControlClient.QueryAppsCallback() {
            @Override
            public void onError(Request request, IOException e) {

            }

            @Override
            public void onFailure(Response response, Throwable throwable) {

            }

            @Override
            public void onSuccess(final List<RokuAppInfo> rokuAppInfoList) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(new RokuAppImageAdapter(getActivity(), rokuAppInfoList));
                    }
                });

            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    RokuExControlClient.KeypressCallback keyPressCallback = new RokuExControlClient.KeypressCallback() {

        @Override
        public void onError(Request request, IOException e) {

        }

        @Override
        public void onFailure(Response response, Throwable throwable) {

        }

        @Override
        public void onSuccess(Response response) {

        }
    };

    private RokuKey getKey(View v) {
        RokuKey key = null;
        if (upBtn == v) {
            key = RokuKey.UP;
        } else if (rightBtn == v) {
            key = RokuKey.RIGHT;
        } else if (downBtn == v) {
            key = RokuKey.DOWN;
        } else if (leftBtn == v) {
            key = RokuKey.LEFT;
        } else if (homeBtn == v) {
            key = RokuKey.HOME;
        } else if (selectBtn == v) {
            key = RokuKey.SELECT;
        } else if (backBtn == v) {
            key = RokuKey.BACK;
        } else if (fwdBtn == v) {
            key = RokuKey.FWD;
        } else if (rwdBtn == v) {
            key = RokuKey.REV;
        } else if (replayBtn == v) {
            key = RokuKey.INSTANTREPLAY;
        }
        return key;
    }

    @Override
    public void onRokuKeyDown(View v) {
        final RokuKey key = getKey(v);
        if (key != null) {
            RokuRemoteFragment.this.rokuClient.keyDown(key, keyPressCallback);
        }
    }

    @Override
    public void onRokuKeyUp(View v) {
        final RokuKey key = getKey(v);
        if (key != null) {
            RokuRemoteFragment.this.rokuClient.keyUp(key, keyPressCallback);
        }
    }

    @Override
    public void onRokuKeyPress(View v) {
        final RokuKey key = getKey(v);
        if (key != null) {
            RokuRemoteFragment.this.rokuClient.keyPress(key, keyPressCallback);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
