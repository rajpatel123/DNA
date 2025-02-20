package com.dnamedeg.Adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dnamedeg.R;

public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private final int indicatorWidth;
    private final Paint paint;

    public HorizontalItemDecoration(Context context) {
        indicatorWidth = context.getResources().getDimensionPixelSize(R.dimen.dp_8);
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.blue)); // Set color as per your requirement
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right += indicatorWidth; // Add indicator width to the right side of each item
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getRight();
            int top = child.getTop();
            int right = left + indicatorWidth;
            int bottom = child.getBottom();

            // Draw indicator
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
