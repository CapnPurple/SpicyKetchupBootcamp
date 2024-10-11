/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package Testing;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;


//CODE STARTS HERE BY VALERIA; THIS WILL NOT WORK AS "PLUG AND PLAY", YOU WILL HAVE TO EDIT THIS
//YOURSELF TO MAKE IT FUNCTIONAL IN THE CODE. THIS IS THE IDEA OF THE CONCEPT.

@TeleOp(name = "Limelight PSEUDO", group = "Testing")
@Disabled
public class SensorLimelight3A extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException
    {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            LLStatus status = limelight.getStatus();
            telemetry.addData("Name", "%s",
                    status.getName());
            telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                    status.getTemp(), status.getCpu(),(int)status.getFps());
            telemetry.addData("Pipeline", "Index: %d, Type: %s",
                    status.getPipelineIndex(), status.getPipelineType());

            LLResult result = limelight.getLatestResult();
            if (result != null) {
                // Access general information
                Pose3D botpose = result.getBotpose();
                double captureLatency = result.getCaptureLatency();
                double targetingLatency = result.getTargetingLatency();
                double parseLatency = result.getParseLatency();
                telemetry.addData("LL Latency", captureLatency + targetingLatency);
                telemetry.addData("Parse Latency", parseLatency);
                telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));
                
                if (result.isValid()) {
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("txnc", result.getTxNC());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("tync", result.getTyNC());

                    telemetry.addData("Botpose", botpose.toString());

                    // Access barcode results (april tags)
                    List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
                    for (LLResultTypes.BarcodeResult br : barcodeResults) {
                        telemetry.addData("Barcode", "Data: %s", br.getData());
                    }

                    //OMITTED EXAMPLES FROM THE QUICKSTART FOR VISIBILITY

                    // Access color results
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    for (ColorResult colorTarget : colorTargets) {
                        double x = detection.getTargetXDegrees(); // Where it is (left-right)
                        double y = detection.getTargetYDegrees(); // Where it is (up-down)
                        double area = colorTarget.getTargetArea(); // size (0-100)
                        telemetry.addData("Color Target", "takes up " + area + "% of the image");

                    // color target extends from your pipleline training. i suggest training based
                    // on differences in "shadow". compare scoring element's flat face to the concave
                    // face and label them as something like "short side" or "long side" (up to you, idk)
                    
                    // HUE IS FOR THE COLOR WITHOUT CHANGES BASED ON LIGHT. You want to adjust for value.
                    // Value measures how much "black" is in a color, and therefore, your shadow.
                    // According to the wiki, use hue inversion for red.
                    
                    // Theoretically, you could, also, in the pipeline, choose based on "direction" on your target
                    // IE, a proper orientation is necessary (square face vs long face)

                    //Based on the target direction (from either value or orientation, that part is up to you to find
                    //out which is more accurate), you should then be able to set your intake/claw/whatever to a 
                    //corresponding orientation you can previously label as a public variable in your hardware map
                    //file or at the top of this program.
                    // example to be found in this repository's "TestMap" file.
                    
                    }
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();

            if (TARGET = "name or target") {
            intakePositionServo = setPosition (shortSide);
            } else if {TARGET = "other name"){
            intakePositionServo = setPosition (longSide);
            } else {
            intakePositionServo = setPosition (away);
            }

            //above outlines a basic if/else for what to do based on target data. 
        }
        limelight.stop();
    }
}

