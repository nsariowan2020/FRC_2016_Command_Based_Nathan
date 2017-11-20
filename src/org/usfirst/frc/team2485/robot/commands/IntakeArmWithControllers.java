package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.OI;
import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeArmWithControllers extends Command {

    public IntakeArmWithControllers() {
    	requires(RobotMap.intakeArm);
    	setInterruptible(true);
    }

    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double pwm = OI.JOYSTICK.getRawAxis(OI.JOYSTICK_Y_PORT);
    	
    	if (pwm >= IntakeArm.ARM_DEADBAND && pwm <= -IntakeArm.ARM_DEADBAND) {
    		pwm = 0;
		}
    	
    	RobotMap.intakeArm.armByManual(pwm);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
