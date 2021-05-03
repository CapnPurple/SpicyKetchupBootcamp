/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package Testing;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "Color Changing LED", group = "Sensor")
public class LEDColor extends LinearOpMode {

    private RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    ColorSensor sensorColor;
    private static String visibleColor = "Nothing";

    @Override
    public void runOpMode() {
        sensorColor = hardwareMap.get(ColorSensor.class, "colSensor");

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;  //COLOR_WAVES_FOREST_PALETTE
        blinkinLedDriver.setPattern(pattern);

        waitForStart();

        while (opModeIsActive()) {
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Color", visibleColor);


            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });
            telemetry.update();

            if (sensorColor.red() >= 300 && sensorColor.green() <= 300 && sensorColor.blue() <= 300) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("red");
            } else if (sensorColor.red() <= 300 && sensorColor.green() >= 300 && sensorColor.blue() <= 300) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.GREEN;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("green");
            } else if (sensorColor.red() <= 300 && sensorColor.green() <= 300 && sensorColor.blue() >= 300) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("blue");
            } else if (sensorColor.red() <= 300 && sensorColor.green() >= 300 && sensorColor.blue() >= 300) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.SKY_BLUE;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("teal");
            } else if (sensorColor.red() >= 300 && sensorColor.green() >= 300 && sensorColor.blue() <= 300) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("yellow");
            } else if (sensorColor.red() >= 300 && sensorColor.green() <= 300 && sensorColor.blue() >= 300 && sensorColor.blue() < 400) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.HOT_PINK;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("pink");
            }  else if (sensorColor.red() >= 500 && sensorColor.green() >= 500 && sensorColor.blue() >= 500) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.WHITE;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("white");
            } else if (sensorColor.red() <= 300 && sensorColor.green() <= 300 && sensorColor.blue() <= 300) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);

                visibleColor = ("nothing or black");
            }
        }

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}
