package com.cool.weather;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class ViewUtils {
    @NonNull
    public static <VB extends ViewBinding> VB inflater(Object object, LayoutInflater inflater) {
        return inflater(object, inflater, null, false);
    }
    @SuppressWarnings("unchecked")
    @NonNull
    public static <VB extends ViewBinding> VB inflater(Object object, LayoutInflater inflater, ViewGroup viewGroup, boolean attachToParent) {
        Class<?> cls = object.getClass();
        while (cls != null) {
            Type type = cls.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
                Class<VB> vbCls = null;
                for(Type argument : typeArguments) {
                    if (ViewBinding.class.isAssignableFrom((Class<VB>) argument)) {
                        vbCls = (Class<VB>) argument;
                        break;
                    }
                }
                if (vbCls == null) {
                    continue;
                }
                VB binding = null;
                try {
                    Method method = vbCls.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                    binding = (VB) method.invoke(null, inflater, viewGroup, attachToParent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (binding == null) {
                    try {
                        Method method = vbCls.getMethod("inflate", LayoutInflater.class, ViewGroup.class);
                        binding = (VB) method.invoke(null, inflater, viewGroup);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    if (binding == null) {
                        try {
                            Method method = vbCls.getMethod("inflate", LayoutInflater.class);
                            binding = (VB) method.invoke(null, inflater);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                if (binding != null) {
                    return binding;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new RuntimeException("Can not inflater binding");
    }


    @SuppressWarnings("unchecked")
    @NonNull
    public static <VH extends RecyclerView.ViewHolder> VH newViewHolder(Object object, ViewBinding binding) {
        Class<?> cls = object.getClass();
        while (cls != null) {
            Type type = cls.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
                Class<VH> vbCls = null;
                for(Type argument : typeArguments) {
                    if (RecyclerView.ViewHolder.class.isAssignableFrom((Class<VH>) argument)) {
                        vbCls = (Class<VH>) argument;
                        break;
                    }
                }
                if (vbCls == null) {
                    continue;
                }
                VH vh = null;
                try {
                    Constructor<VH> constructor = vbCls.getConstructor(binding.getClass());
                    vh = (VH) constructor.newInstance(binding);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (vh != null) {
                    return vh;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new RuntimeException("Can not new viewHolder");
    }


    @SuppressLint({"InternalInsetResource", "DiscouragedApi"})
    public static int getStatusBarHeight(View view) {
        WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(view);
        if (insets != null && insets.isVisible(WindowInsetsCompat.Type.statusBars())) {
            Insets statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            return statusBarInsets.bottom - statusBarInsets.top;
        } else {
            int resourceId = view.getResources().getIdentifier("status_bar_height", "dimen", "android");
            return resourceId > 0 ? view.getResources().getDimensionPixelSize(resourceId) : 0;
        }
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(int dp) {
        return (int) (MyApplication.instance.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static void measureView(View view) {
        measureView(view, (1 << 30) - 1, (1 << 30) - 1);
    }

    public static void measureView(View view, int maxWidth, int maxHeight) {
        if (view.getMeasuredWidth() != 0 && view.getMeasuredHeight() != 0) {
            return;
        }
        int widthSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.AT_MOST);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST);
        view.measure(widthSpec, heightSpec);
    }

    public static void setImageViewTintColor(ImageView imageView, int colorId) {
        Drawable drawable = imageView.getDrawable();
        Drawable newDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(newDrawable, ContextCompat.getColor(imageView.getContext(), colorId));
        imageView.setImageDrawable(newDrawable);
    }

}
