package com.venture.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUp;
    Button btnDown;
    Button btnLeft;
    Button btnRight;

    private static final int GROUND_SIZE = 10;


    FrameLayout ground;
    CustomView view;
    int player_x = 0;
    int player_y = 0;
    int player_size = 50;
    int unit = 0;
    int player_radius = 0;

    final int map[][] = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,2,2,2,0,0,0,0},
            {0,0,0,2,0,2,0,0,0,0},
            {0,0,0,2,0,2,2,2,2,0},
            {0,2,2,2,1,0,1,0,2,0},
            {0,2,0,1,0,0,2,2,2,0},
            {0,2,2,2,2,1,2,0,0,0},
            {0,0,0,0,2,0,2,0,0,0},
            {0,0,0,0,2,2,2,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ground =(FrameLayout) findViewById(R.id.ground);
        btnUp = (Button) findViewById(R.id.btnUp);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);

        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);




        init();

        view = new CustomView(this);
        ground.addView(view);
    }

    private void init(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        unit = metrics.widthPixels / GROUND_SIZE;
        player_radius = unit/2;
        player_x = 0;
        player_y = 0;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUp:
                if (player_y > 0 && !colletionCheck("up")) {
                    if (!boxShift("up")) {
                        player_y = player_y - 1;
                    }
                }
                break;
            case R.id.btnDown:
                if (player_y < GROUND_SIZE - 1 && !colletionCheck("down")) {
                    if (!boxShift("down")) {
                        player_y = player_y + 1;
                    }
                }
                break;
            case R.id.btnLeft:
                if (player_x > 0 && !colletionCheck("left")) {
                    if (!boxShift("left")) {
                        player_x = player_x - 1;
                    }
                }
                break;
            case R.id.btnRight:
                if(player_x < GROUND_SIZE - 1 && !colletionCheck("right")) {
                    if (!boxShift("right")) {
                        player_x = player_x + 1;
                    }
                }
                break;
        }
        //화면을 다시 그려주는 함수 -> 화면을 지운후에 onDraw를 호출해준다.
        view.invalidate();
    }

    private boolean colletionCheck(String direction){
        System.out.println("colletionCheck=========="+player_x);
        switch(direction){
            case "up":
                if(player_y<2 && map[player_y-1][player_x]==1) {
                    return true;
                }
                break;
            case "down":

                if(player_y>GROUND_SIZE-3 && map[player_y+1][player_x]==1) {
                    return true;
                }
                break;
            case "left":
                if(player_x<2 && map[player_y][player_x-1]==1) {
                    return true;
                }
                break;
            case "right":
                if(player_x>GROUND_SIZE-3 && map[player_y][player_x+1]==1) {
                    return true;
                }
                break;
        }

        return false;
    }

    private boolean boxShift(String direction){
        System.out.println("boxShift=========="+player_x);
        switch(direction){
            case "up":
                if(map[player_y-1][player_x]==1) {
                    if(player_y>1) {
                        map[player_y - 1][player_x] = 0;    // 이전 박스를 0으로
                        map[player_y - 2][player_x] = 1;    // 박스가 옮겨지는 위치에 1을 표시
                        return true;
                    }
                }

                break;
            case "down":

                if(map[player_y+1][player_x]==1) {
                    if(player_y<GROUND_SIZE-2) {
                        map[player_y + 1][player_x] = 0;    // 이전 박스를 0으로
                        map[player_y + 2][player_x] = 1;    // 박스가 옮겨지는 위치에 1을 표시
                        return true;
                    }

                }
                break;
            case "left":
                if(map[player_y][player_x-1]==1) {
                    if(player_x>1) {
                        map[player_y][player_x-1] = 0;    // 이전 박스를 0으로
                        map[player_y][player_x-2] = 1;    // 박스가 옮겨지는 위치에 1을 표시
                        return true;
                    }
                }
                break;
            case "right":
                if(map[player_y][player_x+1]==1) {
                    if(player_x<GROUND_SIZE-2) {
                        map[player_y][player_x+1] = 0;    // 이전 박스를 0으로
                        map[player_y][player_x+2] = 1;    // 박스가 옮겨지는 위치에 1을 표시
                        return true;
                    }
                }
                break;
        }

        return false;
    }


    class CustomView extends View {

        Paint magenta = new Paint();
        Paint black   = new Paint();
    public CustomView(Context context) {
        super(context);
        magenta.setColor(Color.MAGENTA);
        black.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // #onDraw함수에서 그림그리기
        canvas.drawCircle(
                player_x*unit+player_radius,
                player_y*unit+player_radius,
                player_size, magenta);

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]!=0){
                    canvas.drawRect(
                            unit*j,
                            unit*i,
                            unit*j+unit,
                            unit*i+unit,
                            black);
                }
            }
        }
    }
}

}
