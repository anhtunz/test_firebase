package com.example.firebase_test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Users;
import static android.content.ContentValues.TAG;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainAdapter extends FirebaseRecyclerAdapter<Users,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Users model) {
//        position = holder.getBindingAdapterPosition();
        holder.hoten.setText(model.getName());
        Log.d(TAG, holder.hoten.toString());
        holder.mssv.setText(String.valueOf(model.getMssv()));
        holder.lop.setText(model.getLop());
        holder.quequan.setText(model.getQuequan());
        Glide.with(holder.img.getContext())
                .load(model.getAvatar()).placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .circleCrop()
                .error(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_text_dark_normal)
                .into(holder.img);

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();
                View view1 = dialogPlus.getHolderView();
                EditText name_edit = view1.findViewById(R.id.edit_name);
                EditText mssv_edit = view1.findViewById(R.id.edit_mssv);
                EditText lop_edit = view1.findViewById(R.id.edit_lop);
                EditText quequan_edit = view1.findViewById(R.id.edit_quequan);

                Button btn_updateConfirm = view1.findViewById(R.id.btn_UpdateConfirm);
                Button btn_delete = view1.findViewById(R.id.btn_delete);
                name_edit.setText(model.getName());
                mssv_edit.setText(String.valueOf(model.getMssv()));
                lop_edit.setText(model.getLop());
                quequan_edit.setText(model.getQuequan());
                dialogPlus.show();

                btn_updateConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name_edit.getText().toString());
                        map.put("mssv", Long.parseLong(mssv_edit.getText().toString()));
                        map.put("lop",lop_edit.getText().toString());
                        map.put("quequan",quequan_edit.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.hoten.getContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "Cập nhật thành công ");
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.hoten.getContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "Cập nhật thất cmn bại rồi ");
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Da goi hàm xóa ");
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.hoten.getContext());
                builder.setTitle("Bạn có muốn xóa");
                builder.setMessage("Dữ liệu sẽ không thể khôi phục lại");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.hoten.getContext(),"Đã hủy",Toast.LENGTH_SHORT);
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView hoten, mssv, lop, quequan;

        Button btn_update, btn_delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.urlImage);
            hoten = (TextView)itemView.findViewById(R.id.username);
            mssv = (TextView)itemView.findViewById(R.id.mssv);
            lop = (TextView)itemView.findViewById(R.id.lop);
            quequan = (TextView)itemView.findViewById(R.id.quequan);
            btn_update = (Button)itemView.findViewById(R.id.btn_edit);
            btn_delete = (Button)itemView.findViewById(R.id.btn_delete);

        }
    }

}
