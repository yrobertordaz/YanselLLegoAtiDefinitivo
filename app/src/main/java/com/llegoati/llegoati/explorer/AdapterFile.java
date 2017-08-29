package com.llegoati.llegoati.explorer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.llegoati.llegoati.R;
import com.llegoati.llegoati.infrastructure.abstracts.IRepository;
import com.llegoati.llegoati.infrastructure.concrete.SqliteRepository;
import com.llegoati.llegoati.infrastructure.concrete.dbconection;
import com.llegoati.llegoati.smsmodulo.Utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Richard on 01/09/2016.
 */
public class AdapterFile extends  RecyclerView.Adapter<AdapterFile.FileViewHolder>{

    public ArrayList<FileInformation> mFiles;
    public ExplorerActivity mActivity;
    public RecyclerView mRv;
    public PreferencesFactory mPreferencesFactory;

    public AdapterFile(ArrayList<FileInformation> mFiles, ExplorerActivity mActivity) {
        super();
        this.mRv = mRv;
        this.mFiles = mFiles;
        this.mActivity = mActivity;
        mPreferencesFactory = new PreferencesFactory(mActivity);

    }

    public void clear(){
        mFiles.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<FileInformation> lista){
        mFiles.addAll(lista);
        notifyDataSetChanged();
    }

    //estos dos son la opcion ideal para actualizarlo todo aqui mismo
    public void addItem(FileInformation alumno) {
        mFiles.add(alumno);
        notifyDataSetChanged();
        notifyItemInserted(mFiles.indexOf(alumno));
        notifyItemRangeChanged(0,mFiles.size());
    }

    // Elimina un elemento del adaptador.
    public void remove(FileInformation item) {
        int position = mFiles.indexOf(item);
        mFiles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,mFiles.size());

    }

    @Override
    public void onBindViewHolder(final FileViewHolder fileViewHolder, final int i) {

        String name = mFiles.get(i).getmName();
        fileViewHolder.mName.setText(name);

        if(mFiles.get(i).mIsFolder){
            fileViewHolder.mImage.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_folder_48pt));
        }else{
            fileViewHolder.mImage.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.fbui_notes_l));
        }

        final FileInformation file = mFiles.get(i);
        final String patch = file.getPatch() + "/" + file.getmName();

        final File temp = new File(patch);
        Date lastModDate = new Date(temp.lastModified());
        CharSequence s = DateFormat.format("MMMM d, yyyy ",lastModDate);
        fileViewHolder.mUpdateDate.setText(s);

        if(temp.isFile()){
            long size = temp.length();
            float sw = size /1024;
            fileViewHolder.mSize.setText(sw + " kb");
        }else {

            int total = 0;
            if (temp.list()!=null) {
                total = temp.list().length;
            }

            fileViewHolder.mSize.setText(total + " ficheros");

        }
      /* GestureListener mgl = new GestureListener(mActivity);
       final GestureDetector gd = new GestureDetector(mActivity,mgl);

        fileViewHolder.mClick.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(gd.onTouchEvent(event)) {

                    return true;
                }
                return false;
            }
        });*/

            fileViewHolder.mClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.ismIsFolder()) {

                    mActivity.update(patch);

                }else{
                        
                        checkFile(patch,fileViewHolder.mClick.getContext());

                    }


                }
            });



    }



    private void checkFile(String patch, Context mContext) {
        try {
            File mF = new File(patch);
            String[] mPatch = mF.getAbsolutePath().split(File.separator);
            String mFinalPath = "";
            for (int i=0;i<mPatch.length-1;i++){
                mFinalPath += mPatch[i] + File.separator;
            }

            if (mActivity.getmRepository() instanceof SqliteRepository){
                ((SqliteRepository)mActivity.getmRepository()).setmDbconection(new dbconection(mContext,mFinalPath,mF.getName()));
                mActivity.getmRepository().validate();
            }else {
                IRepository mRepo = new SqliteRepository(new dbconection(mContext,mFinalPath,mF.getName()));
                mRepo.validate();
            }



                mPreferencesFactory.addDataBase(new LLegoDataBaseFactory(mF.getName(),Constants.DB_DEFAULT,mFinalPath));
            if (!Constants.ADD_DATABASE) {
                mPreferencesFactory.setDbPath(patch);

            }else {
                Constants.ADD_DATABASE = false;
            }

            mActivity.finish();

        }catch (Exception e){
            Toast.makeText(mActivity,"Error en archivo",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_file, viewGroup, false);
        return new FileViewHolder(v,mActivity);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mName;
        public ImageView mImage;
        public LinearLayout mClick;
        public TextView mSize;
        public TextView mUpdateDate;



        FileViewHolder(View itemView, final Activity mActivity) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.file_name);
            mImage = (ImageView) itemView.findViewById(R.id.file_img);
            mClick = (LinearLayout) itemView.findViewById(R.id.file_click);
            mSize = (TextView) itemView.findViewById(R.id.file_size);
            mUpdateDate = (TextView) itemView.findViewById(R.id.file_date_update);



        }


    }


}
