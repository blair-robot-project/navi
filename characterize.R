#Smooths a value while taking its derivative with respect to time.
smoothDerivative <- function(value, timeMillis, n){
  smoothed <- (value[(n+1):length(value)] - value[1:(length(value)-n)])/((timeMillis[(n+1):length(timeMillis)] - timeMillis[1:(length(timeMillis)-n)])/1000);
  return(c(rep(0, ceiling(n/2)), smoothed, rep(0, floor(n/2))));
}

characterizeDrive <- function(velFile, accelFile, smoothing = 2){
  vel <- read.csv(velFile)
  accel <- read.csv(accelFile)
  goodVel <- subset(vel, abs(left.velocity) > 0.1 & left.voltage!=0 & abs(right.velocity) > 0.1 & right.voltage!=0)
  goodVel$left_accel <- smoothDerivative(goodVel$left.velocity, goodVel$time, smoothing)
  goodVel$right_accel <- smoothDerivative(goodVel$right.velocity, goodVel$time, smoothing)
  accel$left_accel <- smoothDerivative(accel$left.velocity, accel$time, smoothing)
  accel$right_accel <- smoothDerivative(accel$right.velocity, accel$time, smoothing)
  goodAccel <- subset(accel, left.voltage != 0 & right.voltage != 0)
  goodAccelLeft <- goodAccel[(which.max(abs(goodAccel$left_accel))+1):length(goodAccel$time),]
  goodAccelRight <- goodAccel[(which.max(abs(goodAccel$right_accel))+1):length(goodAccel$time),]
  combinedLeftVoltage <- c(goodVel$left.voltage, goodAccelLeft$left.voltage)
  combinedRightVoltage <- c(goodVel$right.voltage, goodAccelRight$right.voltage)
  combinedLeftVel <- c(goodVel$left.velocity, goodAccelLeft$left.velocity)
  combinedRightVel <- c(goodVel$right.velocity, goodAccelRight$right.velocity)
  combinedLeftAccel <- c(goodVel$left_accel, goodAccelLeft$left_accel)
  combinedRightAccel <- c(goodVel$right_accel, goodAccelRight$right_accel)
  plot(goodAccelLeft$time, goodAccelLeft$left_accel)
  plot(goodVel$time, goodVel$left.voltage)
  plot(goodVel$left.voltage, goodVel$left.velocity)
  leftModel <- lm(combinedLeftVoltage~combinedLeftVel+combinedLeftAccel)
  rightModel <- lm(combinedRightVoltage~combinedRightVel+combinedRightAccel)
  print(summary(leftModel))
  print(summary(rightModel))
}

characterizeElevator <- function(velFile, accelFile, smoothing = 2){
  vel <- read.csv(velFile)
  accelDat <- read.csv(accelFile)
  goodVel <- subset(vel, abs(elevatorTalon.velocity) > 0.1 & elevatorTalon.voltage!=0)
  goodVel$accel <- smoothDerivative(goodVel$elevatorTalon.velocity, goodVel$time, smoothing)
  accelDat$accel <- smoothDerivative(accelDat$elevatorTalon.velocity, accelDat$time, smoothing)
  goodAccel <- subset(accelDat, elevatorTalon.voltage != 0)
  goodAccel <- goodAccel[(which.max(abs(goodAccel$laccel))+1):length(goodAccel$time),]
  combinedVoltage <- c(goodVel$elevatorTalon.voltage, goodAccel$elevatorTalon.voltage)
  combinedVel <- c(goodVel$elevatorTalon.velocity, goodAccel$elevatorTalon.velocity)
  combinedAccel <- c(goodVel$accel, goodAccel$accel)
  plot(goodAccel$time, goodAccel$accel)
  plot(goodVel$time, goodVel$elevatorTalon.voltage)
  plot(goodVel$elevatorTalon.voltage, goodVel$elevatorTalon.velocity)
  model <- lm(combinedVoltage~combinedVel+combinedAccel)
  print(summary(model))
}