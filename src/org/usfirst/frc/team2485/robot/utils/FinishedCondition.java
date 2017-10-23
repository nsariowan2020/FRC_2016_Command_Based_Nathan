package org.usfirst.frc.team2485.robot.utils;

public interface FinishedCondition {
	public boolean isFinished();
	public static final FinishedCondition FALSE_CONDITION = () -> {
		return false;
	};
}
