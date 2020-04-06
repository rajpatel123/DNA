package com.dnamedical.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnamedical.Models.paidvideo.PaidVideoResponse;
import com.dnamedical.Models.paidvideo.Price;
import com.dnamedical.R;
import com.dnamedical.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class VideoListPriceAdapter extends RecyclerView.Adapter<VideoListPriceAdapter.ViewHolder> {


    private Context applicationContext;
    private List<Price> priceList;
    ArrayList<Price> priceArrayList;

    PaidVideoResponse paidVideoResponse;


    VideoListPriceAdapter.OnCategoryClick onUserClickCallback;
    VideoListPriceAdapter.OnBuyNowClick onUserBuyNowClick;
    VideoListPriceAdapter.OnDataClick onDataClick;
    private int visible;

    public VideoListPriceAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
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
        holder.number.setText("" + (holder.getAdapterPosition() + 1));

        holder.ratingandtime.setText(price.getDuration() + " min video");
        //Log.i("Thumb",  price.getUrl());
        Picasso.with(applicationContext).load(price.getDrImg())
                .error(R.drawable.profile_image_know_more)
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

        if ((!TextUtils.isEmpty(price.getPaymentStatus()) && price.getPaymentStatus().equalsIgnoreCase("1")) || (!TextUtils.isEmpty(price.getSubscription_status()) && price.getSubscription_status().equalsIgnoreCase("1"))) {
            holder.buyNow.setVisibility(View.GONE);
            holder.lockNew.setVisibility(GONE);
            holder.txtActualPrice.setVisibility(View.GONE);
            holder.txtTotalPrice.setVisibility(View.GONE);
            if (price.getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/")) {
                holder.commingSoon.setVisibility(View.VISIBLE);
            } else {
                holder.commingSoon.setVisibility(GONE);
            }

        } else {
            holder.buyNow.setVisibility(View.VISIBLE);
            holder.txtActualPrice.setVisibility(View.VISIBLE);
            holder.txtTotalPrice.setVisibility(View.VISIBLE);
            holder.lockNew.setVisibility(View.VISIBLE);
            if (price.getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/")) {
                holder.commingSoon.setVisibility(View.VISIBLE);
            } else {
                holder.commingSoon.setVisibility(GONE);
            }
        }

            ////////////////////////////////////////////////////
//        if ((!TextUtils.isEmpty(price.getPaymentStatus()) && price.getPaymentStatus().equalsIgnoreCase("1")) || (!TextUtils.isEmpty(price.getSubscription_status()) && price.getSubscription_status().equalsIgnoreCase("1"))) {
//            holder.pdfloadImg.setVisibility(visible);
//            if (price.getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/")) {
//                holder.commingSoon.setVisibility(View.VISIBLE);
//            } else {
//                holder.commingSoon.setVisibility(GONE);
//            }
//
//        } else {
//            holder.buyNow.setVisibility(View.VISIBLE);
//            holder.pdfloadImg.setVisibility(visible);
//            if (price.getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/")) {
//                holder.commingSoon.setVisibility(View.VISIBLE);
//            } else {
//                holder.commingSoon.setVisibility(GONE);
//            }
//
//        }


        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!TextUtils.isEmpty(price.getPaymentStatus()) && price.getPaymentStatus().equalsIgnoreCase("1")) && (!TextUtils.isEmpty(price.getUrl()) && !price.getUrl().equalsIgnoreCase("http://13.232.100.13/img/file/"))) {
                    if (onUserClickCallback != null) {
                        onUserClickCallback.onCateClick(priceList.get(holder.getAdapterPosition()));
                    } else {
                        Utils.displayToast(applicationContext, "Coming Soon");

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

                if (priceList.get(holder.getAdapterPosition()).getIsbuyall().trim().equalsIgnoreCase("1")) {
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

                            onDataClick.onNextActivityDataClick();
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


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_view)
        LinearLayout row_view;
        @BindView(R.id.number)
        TextView number;

        @BindView(R.id.vid_title)
        TextView title;


        @BindView(R.id.vid_doctor_name)
        TextView doctarName;
        @BindView(R.id.chapter)
        TextView chapter;

        @BindView(R.id.ratingandtime)
        TextView ratingandtime;

        @BindView(R.id.pdfloadImg)
        ImageView pdfloadImg;

        @BindView(R.id.image_doctor)
        ImageView imageViewDoctor;

        @BindView(R.id.buy_now)
        TextView buyNow;

        @BindView(R.id.commingsoon)
        TextView commingSoon;

        @BindView(R.id.lock)
        ImageView lockNew;


        @BindView(R.id.txt_actual_price)
        TextView txtActualPrice;


        @BindView(R.id.txt_total_price)
        TextView txtTotalPrice;

        @BindView(R.id.lineView)
        View lineView;
        @BindView(R.id.lineViewWithMargin)
        View lineViewWithMargin;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnCategoryClick {
        public void onCateClick(Price price);
        //public void onNextActivityDataClick();
    }

    public interface OnDataClick {
        // public void ondataClick(PaidVideoResponse price);
        public void onNextActivityDataClick();
    }

    public interface OnBuyNowClick {
        public void onBuyNowCLick(String couponCode, String id, String title, String couponValue, String subTitle, String discount, String price, String shippingCharge);

    }


}
