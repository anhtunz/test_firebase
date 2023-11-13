package adapter;
import static android.content.ContentValues.TAG;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_test.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Users;

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
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Users model) {
        holder.hoten.setText(model.getName());
        Log.d(TAG, "abc.sxyz");
        holder.mssv.setText((int) model.getMssv());
        holder.lop.setText(model.getLop());
        holder.quequan.setText(model.getQuequan());
        Glide.with(holder.img.getContext())
                .load(model.getAvatar()).placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .circleCrop()
                .error(com.google.firebase.appcheck.interop.R.drawable.common_google_signin_btn_text_dark_normal)
                .into(holder.img);
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

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.urlImage);
            hoten = (TextView)itemView.findViewById(R.id.username);
            mssv = (TextView)itemView.findViewById(R.id.mssv);
            lop = (TextView)itemView.findViewById(R.id.lop);
            quequan = (TextView)itemView.findViewById(R.id.quequan);
        }
    }

}
