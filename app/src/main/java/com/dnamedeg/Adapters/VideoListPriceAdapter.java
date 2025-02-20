package com.dnamedeg.Adapters;

import android.content.Context;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dnamedeg.BuildConfig;
import com.dnamedeg.Models.paidvideo.PaidVideoResponse;
import com.dnamedeg.Models.paidvideo.Price;
import com.dnamedeg.R;
import com.dnamedeg.utils.Constants;
import com.dnamedeg.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



import static android.view.View.GONE;

public class VideoListPriceAdapter extends RecyclerView.Adapter<VideoListPriceAdapter.ViewHolder> {

    int contentType;
    private Context applicationContext;
    private List<Price> priceList;
    ArrayList<Price> priceArrayList;
    String isFull;
    PaidVideoResponse paidVideoResponse;


    VideoListPriceAdapter.OnCategoryClick onUserClickCallback;
    VideoListPriceAdapter.OnBuyNowClick onUserBuyNowClick;
    VideoListPriceAdapter.OnDataClick onDataClick;
    private int visible;

    public VideoListPriceAdapter(Context applicationContext, String isfull) {
        this.applicationContext = applicationContext;
        this.isFull = isfull;
    }


    public void setPaidVideoResponse(PaidVideoResponse paidVideoResponse) {
        this.paidVideoResponse = paidVideoResponse;
    }

    public void setOnDataClick(OnDataClick onDataClick) {
        this.onDataClick = onDataClick;
    }

    public void setOnUserClickCallback(OnCategoryClick onUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback;
    }

    public void setOnBuyNowClick(OnBuyNowClick onBuyNowClick) {
        this.onUserBuyNowClick = onBuyNowClick;
    }

    @NonNull
    @Override
    public VideoListPriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.paid_video_item, viewGroup, false);
        return new VideoListPriceAdapter.ViewHolder(view);

    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoListPriceAdapter.ViewHolder holder, int i) {


        Price price = priceList.get(i);


        if (price.getTitle() != null) {
            holder.title.setText("" + price.getTitle());
            holder.title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.title.setMarqueeRepeatLimit(5);
            holder.title.setSelected(true);
        }

        if (!TextUtils.isEmpty(price.getCh_name())) {
            holder.chapter.setText("" + price.getCh_name());
        } else {
            holder.chapter.setText("Not Assigned Chapter");
        }
        if (holder.getAdapterPosition() > 0) {
            if (!TextUtils.isEmpty(price.getCh_name())) {
                if (!price.getCh_name().equalsIgnoreCase(priceList.get(holder.getAdapterPosition() - 1).getCh_name())) {
                    holder.chapter.setVisibility(View.VISIBLE);
                    holder.lineView.setVisibility(GONE);
                    holder.lineViewWithMargin.setVisibility(View.VISIBLE);
                    //  holder.lineView.setLayoutParams(getLayoutParams(true));
                } else {
                    //holder.lineView.setLayoutParams(getLayoutParams(false));
                    holder.chapter.setVisibility(GONE);
                    holder.lineView.setVisibility(View.VISIBLE);
                    holder.lineViewWithMargin.setVisibility(GONE);
                }
            }

        }

        if (price.getSubTitle() != null) {
            holder.doctarName.setText("" + price.getSubTitle());
        }
        if (Integer.parseInt(price.getDuration()) > 0) {
            holder.ratingandtime.setText(price.getDuration() + " min video");
        } else {
            holder.ratingandtime.setText("N/A");

        }


        if (TextUtils.isEmpty(price.getPdf_url())) {
            holder.pdf_notes.setVisibility(GONE);
        } else {
            holder.pdf_notes.setVisibility(View.VISIBLE);

        }
        holder.number.setText("" + (holder.getAdapterPosition() + 1));

        holder.ratingandtime.setText(price.getDuration() + " min video");
        //Log.i("Thumb",  price.getUrl());
        Glide.with(applicationContext).load(price.getDrImg())
                .error(R.drawable.dnalogo)
                .into(holder.imageViewDoctor);

        if (price.getPrice() != null) {
            String num = String.valueOf(Integer.parseInt(price.getPrice()) - Integer.parseInt(price.getCoupanValue()));
            holder.txtActualPrice.setText("" + "INR " + "" + price.getPrice());
            holder.txtActualPrice.setPaintFlags(holder.txtActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        if (price.getCoupanValue() != null) {
            String num = String.valueOf(Integer.parseInt(price.getPrice()) - ((Integer.parseInt(price.getPrice()) * Integer.parseInt(price.getCoupanValue())) / 100));
            holder.txtTotalPrice.setText("" + "INR " + "" + num);

        }


        if ((!TextUtils.isEmpty(price.getPaymentStatus()) && price.getPaymentStatus().equalsIgnoreCase("1"))) {
            holder.buyNow.setVisibility(View.GONE);
            holder.lockNew.setVisibility(GONE);
            holder.txtActualPrice.setVisibility(View.GONE);
            holder.txtTotalPrice.setVisibility(View.GONE);
            if (price.getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "img/file/")) {
                holder.commingSoon.setVisibility(View.VISIBLE);
            } else {
                holder.commingSoon.setVisibility(GONE);

            }
        } else if ((!TextUtils.isEmpty(price.getSubscription_status()) && price.getSubscription_status().equalsIgnoreCase("1"))) {
            if (Constants.IS_NEET) {
                holder.buyNow.setVisibility(View.GONE);
                holder.lockNew.setVisibility(GONE);
                holder.txtActualPrice.setVisibility(View.GONE);
                holder.txtTotalPrice.setVisibility(View.GONE);
                if (price.getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")) {
                    holder.commingSoon.setVisibility(View.VISIBLE);
                } else {
                    holder.commingSoon.setVisibility(GONE);

                }
            } else {
                holder.buyNow.setVisibility(View.VISIBLE);
                holder.txtActualPrice.setVisibility(View.VISIBLE);
                holder.txtTotalPrice.setVisibility(View.VISIBLE);
                holder.lockNew.setVisibility(View.VISIBLE);
                if (price.getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")) {
                    holder.commingSoon.setVisibility(View.VISIBLE);
                } else {
                    holder.commingSoon.setVisibility(GONE);
                }
            }
        } else {
            holder.buyNow.setVisibility(View.VISIBLE);
            holder.txtActualPrice.setVisibility(View.VISIBLE);
            holder.txtTotalPrice.setVisibility(View.VISIBLE);
            holder.lockNew.setVisibility(View.VISIBLE);
            if (price.getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")) {
                holder.commingSoon.setVisibility(View.VISIBLE);
            } else {
                holder.commingSoon.setVisibility(GONE);
            }

        }


        if (holder.buyNow.getVisibility() == View.VISIBLE || holder.pdf_notes.getVisibility() == View.VISIBLE) {
            holder.line_separator.setVisibility(View.VISIBLE);
        } else {
            holder.line_separator.setVisibility(GONE);

        }
        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((!TextUtils.isEmpty(price.getPaymentStatus()) && price.getPaymentStatus().equalsIgnoreCase("1")) && (!TextUtils.isEmpty(price.getUrl()) && !price.getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/")))) {
                    if (onUserClickCallback != null) {
                        switch (contentType) {
                            case 1:
                                onUserClickCallback.onCateClick(priceList.get(holder.getAdapterPosition()));
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    } else {
                        Utils.displayToast(applicationContext, "Coming Soon");

                    }
                } else if ((!TextUtils.isEmpty(price.getSubscription_status()) && price.getSubscription_status().equalsIgnoreCase("1"))) {
                    if (Constants.IS_NEET) {
                        if (onUserClickCallback != null) {
                            switch (contentType) {
                                case 1:
                                    onUserClickCallback.onCateClick(priceList.get(holder.getAdapterPosition()));
                                    break;
                                case 2:
                                    onDataClick.onNotesClick(price.getPdf_url());
                                    break;
                                case 3:
                                    onDataClick.eBookClick(price.getEbook_url());
                                    break;
                            }
                        } else {
                            Utils.displayToast(applicationContext, "Coming Soon");

                        }
                    }
                }

            }

        });


        holder.pdf_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!TextUtils.isEmpty(price.getPaymentStatus()) && price.getPaymentStatus().equalsIgnoreCase("1")) && (!TextUtils.isEmpty(price.getUrl()) && !price.getUrl().equalsIgnoreCase(BuildConfig.API_SERVER_IP + "/img/file/"))) {
                    if (onDataClick != null) {
                        onDataClick.onNotesClick(price.getPdf_url());
                    }
                }

            }
        });

        holder.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(applicationContext);
                // ...Irrelevant code for customizing the buttons and titl
                LayoutInflater inflater = LayoutInflater.from(applicationContext);
                View dialogView = inflater.inflate(R.layout.buy_now_alert_dialog, null);
                dialogBuilder.setView(dialogView);

                final android.app.AlertDialog dialog = dialogBuilder.create();
                Button btn_yes = dialogView.findViewById(R.id.btn_done);
                TextView ortxt = dialogView.findViewById(R.id.orTxt);
                Button btn_yes_all = dialogView.findViewById(R.id.btn_done_all);

                btn_yes_all.setText("Click Here To Buy All Topic");

                if (isFull.equalsIgnoreCase("1")) {
                    btn_yes.setVisibility(GONE);
                    ortxt.setVisibility(GONE);

                } else {
                    btn_yes.setVisibility(View.VISIBLE);
                    ortxt.setVisibility(View.VISIBLE);

                }
                Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn_yes_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onDataClick != null) {

                            onDataClick.onBuyAllVideo();
                        }
                        dialog.dismiss();
                    }
                });

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onUserBuyNowClick != null) {
                            onUserBuyNowClick.onBuyNowCLick(priceList.get(holder.getAdapterPosition()).getCoupanCode(),
                                    priceList.get(holder.getAdapterPosition()).getId(),
                                    priceList.get(holder.getAdapterPosition()).getTitle(),
                                    priceList.get(holder.getAdapterPosition()).getCoupanValue(),
                                    priceList.get(holder.getAdapterPosition()).getSubTitle(),
                                    priceList.get(holder.getAdapterPosition()).getDiscount(),
                                    priceList.get(holder.getAdapterPosition()).getPrice(),
                                    priceList.get(holder.getAdapterPosition()).getSalereport(),
                                    priceList.get(holder.getAdapterPosition()).getFaculty_email(),

                                    priceList.get(holder.getAdapterPosition()).getShippingCharge());
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }


        });
    }

    private ViewGroup.LayoutParams getLayoutParams(boolean marginTop) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        if (marginTop) {
            layoutParams.setMargins(0, 50, 0, 0);
        } else {
            layoutParams.setMargins(0, 0, 0, 0);
        }
        return layoutParams;
    }

    @Override
    public int getItemCount() {
        if (priceList != null && priceList.size() > 0) {
            return priceList.size();
        } else {
            return 0;
        }

    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        LinearLayout row_view;

        TextView number;


        TextView title;



        TextView doctarName;

        TextView chapter;


        TextView ratingandtime;



        ImageView imageViewDoctor;


        TextView buyNow;



        View line_separator;


        TextView commingSoon;


        ImageView lockNew;



        TextView txtActualPrice;


        ImageView pdf_notes;



        TextView txtTotalPrice;


        View lineView;

        View lineViewWithMargin;

        public ViewHolder(View view) {
            super(view);

            row_view=view.findViewById(R.id.row_view);
            title=view.findViewById(R.id.vid_title);

            pdf_notes=view.findViewById(R.id.pdf_notes);
            ratingandtime=view.findViewById(R.id.ratingandtime);
            lockNew=view.findViewById(R.id.lock);
            txtActualPrice=view.findViewById(R.id.txt_actual_price);
            txtTotalPrice=view.findViewById(R.id.txt_total_price);
            doctarName=view.findViewById(R.id.vid_doctor_name);
            chapter=view.findViewById(R.id.chapter);
            ratingandtime=view.findViewById(R.id.ratingandtime);
            imageViewDoctor=view.findViewById(R.id.image_doctor);
            buyNow=view.findViewById(R.id.buy_now);
            chapter=view.findViewById(R.id.chapter);
            number=view.findViewById(R.id.number);
            commingSoon=view.findViewById(R.id.commingsoon);
            line_separator=view.findViewById(R.id.line_separator);


            lineView=view.findViewById(R.id.lineView);
            lineViewWithMargin=view.findViewById(R.id.lineViewWithMargin);

        }
    }

    public interface OnCategoryClick {
        void onCateClick(Price price);
        //public void onNextActivityDataClick();
    }

    public interface OnDataClick {
        // public void ondataClick(PaidVideoResponse price);
        void onBuyAllVideo();

        void onNotesClick(String url);

        void eBookClick(String url);
    }

    public interface OnBuyNowClick {
        void onBuyNowCLick(String couponCode, String id, String title, String couponValue, String subTitle, String discount, String price, String salesReport, String fEmail, String shippingCharge);


    }


}
