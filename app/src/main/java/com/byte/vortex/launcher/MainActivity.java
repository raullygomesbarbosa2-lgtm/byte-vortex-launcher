package com.byte.vortex.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {
    private LinearLayout appGrid;
    private final int cyan = Color.rgb(0, 229, 255);

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(5, 12, 30));
        getWindow().setNavigationBarColor(Color.rgb(5, 12, 30));

        ScrollView scroll = new ScrollView(this);
        GradientDrawable bg = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(4, 10, 28), Color.rgb(10, 20, 55), Color.rgb(20, 5, 45)});
        scroll.setBackground(bg);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(28), dp(20), dp(24));
        scroll.addView(root);

        TextView brand = new TextView(this);
        brand.setText("BYTE VORTEX");
        brand.setTextColor(cyan);
        brand.setTextSize(30);
        brand.setTypeface(Typeface.create("sans-serif-black", Typeface.BOLD));
        brand.setGravity(Gravity.CENTER);
        root.addView(brand);

        TextView subtitle = new TextView(this);
        subtitle.setText("EMULADORES  •  GAMING HUB");
        subtitle.setTextColor(Color.WHITE);
        subtitle.setTextSize(12);
        subtitle.setLetterSpacing(0.18f);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
        sp.setMargins(0, dp(4), 0, dp(24));
        root.addView(subtitle, sp);

        TextView section = new TextView(this);
        section.setText("SEUS APLICATIVOS");
        section.setTextColor(Color.WHITE);
        section.setTextSize(15);
        section.setTypeface(null, Typeface.BOLD);
        root.addView(section);

        appGrid = new LinearLayout(this);
        appGrid.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams gp = new LinearLayout.LayoutParams(-1, -2);
        gp.setMargins(0, dp(12), 0, 0);
        root.addView(appGrid, gp);

        TextView footer = new TextView(this);
        footer.setText("BYTE VORTEX  |  ANDROID EDITION");
        footer.setTextColor(Color.LTGRAY);
        footer.setTextSize(10);
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(-1, -2);
        fp.setMargins(0, dp(28), 0, dp(8));
        root.addView(footer, fp);

        setContentView(scroll);
        loadApps();
    }

    private void loadApps() {
        appGrid.removeAllViews();
        Intent query = new Intent(Intent.ACTION_MAIN, null);
        query.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> apps = pm.queryIntentActivities(query, 0);
        Collections.sort(apps, new Comparator<ResolveInfo>() {
            @Override public int compare(ResolveInfo a, ResolveInfo b) {
                return a.loadLabel(pm).toString().compareToIgnoreCase(b.loadLabel(pm).toString());
            }
        });

        ArrayList<ResolveInfo> filtered = new ArrayList<>();
        for (ResolveInfo info : apps) {
            if (!info.activityInfo.packageName.equals(getPackageName())) filtered.add(info);
        }

        if (filtered.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("Nenhum aplicativo encontrado.");
            empty.setTextColor(Color.WHITE);
            appGrid.addView(empty);
            return;
        }

        for (int i = 0; i < filtered.size(); i += 3) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            for (int j = i; j < Math.min(i + 3, filtered.size()); j++) {
                ResolveInfo info = filtered.get(j);
                LinearLayout tile = new LinearLayout(this);
                tile.setOrientation(LinearLayout.VERTICAL);
                tile.setGravity(Gravity.CENTER);
                tile.setPadding(dp(5), dp(12), dp(5), dp(12));
                GradientDrawable shape = new GradientDrawable();
                shape.setColor(Color.argb(90, 10, 35, 75));
                shape.setCornerRadius(dp(16));
                shape.setStroke(dp(1), Color.argb(180, 0, 210, 255));
                tile.setBackground(shape);

                ImageView icon = new ImageView(this);
                try { icon.setImageDrawable(info.loadIcon(getPackageManager())); } catch (Exception ignored) {}
                LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(dp(42), dp(42));
                tile.addView(icon, ip);

                TextView name = new TextView(this);
                name.setText(info.loadLabel(getPackageManager()));
                name.setTextColor(Color.WHITE);
                name.setTextSize(11);
                name.setGravity(Gravity.CENTER);
                name.setMaxLines(2);
                LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1, dp(34));
                np.setMargins(0, dp(6), 0, 0);
                tile.addView(name, np);

                final String pkg = info.activityInfo.packageName;
                tile.setOnClickListener(v -> {
                    Intent launch = getPackageManager().getLaunchIntentForPackage(pkg);
                    if (launch != null) startActivity(launch);
                });
                LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(0, dp(112), 1);
                tp.setMargins(dp(4), dp(4), dp(4), dp(4));
                row.addView(tile, tp);
            }
            while (row.getChildCount() < 3) {
                View spacer = new View(this);
                row.addView(spacer, new LinearLayout.LayoutParams(0, dp(112), 1));
            }
            appGrid.addView(row);
        }
    }

    @Override protected void onResume() { super.onResume(); if (appGrid != null) loadApps(); }
    private int dp(float value) { return (int)(value * getResources().getDisplayMetrics().density + 0.5f); }
}
