import java.util.Scanner;
public class Main {
    final static int floors = 10;
    final static int liftsCount = 5;
    static Lift [] lifts = new Lift[liftsCount];
    public static void main(String [] args){
        Scanner s = new Scanner(System.in);
        // System.out.println("Current Floor : ");
        // int currentFloor = s.nextInt();
        // System.out.println("Destination Floor : ");
        // int destinationFloor = s.nextInt();
        s.close();
        for(int i = 0; i < liftsCount; i++){
            lifts[i] = new Lift("L" + (i + 1), 0);
        }
        // displayLifts();
        // assignLift(currentFloor, destinationFloor);
        // assignNearestLift(4, 10);
        // assignNearestLift(2, 10);
        // assignNearestLift(9, 0);
        // lifts[0].currFloor = 10;
        // lifts[1].currFloor = 2;
        // lifts[2].currFloor = 5;
        // lifts[3].currFloor = 9;
        restrictLifts(0, 0, 5);
        restrictLifts(1, 0, 5);
        restrictLifts(2, 6, 10);
        restrictLifts(3, 6, 10);
        assignNearestLiftOfSameDirection(1, 6);
    }

    public static void displayLifts(){
        StringBuilder liftsString = new StringBuilder("Lift : ");
        StringBuilder floorString = new StringBuilder("Floor : ");
        for(Lift lift : lifts){
            liftsString.append(" ");
            liftsString.append(lift.name);
            liftsString.append(" ");
            floorString.append(" ");
            floorString.append(lift.currFloor);
            floorString.append("  ");
        }
        System.out.println(liftsString);
        System.out.println(floorString);
    }

    public static void assignLift(int currentFloor, int destinationFloor){
        Lift result = null;
        for(Lift lift : lifts){
            if(!lift.restrictedFloors[currentFloor] && !lift.restrictedFloors[destinationFloor]){
                result = lift;
                if(result != null)  break;
            }
        }
        System.out.println(result.name + " is assigned");
        result.currFloor = destinationFloor;
        displayLifts();
    }

    public static void assignNearestLift(int currentFloor, int destinationFloor){
        Lift nearestLift = new Lift("lift", Integer.MAX_VALUE);
        int minDiff = Math.abs(nearestLift.currFloor - currentFloor);
        for(Lift lift : lifts){
            if(!lift.restrictedFloors[currentFloor] && !lift.restrictedFloors[destinationFloor]){
                int diff = Math.abs(lift.currFloor - currentFloor);
                if(minDiff > diff){
                    minDiff = diff;
                    nearestLift = lift;
                }
            }
        }
        if(nearestLift.name.equals("lift")) System.out.println("Error! Cannot assign a lift");
        else    System.out.println(nearestLift.name + " is assigned");
        nearestLift.currFloor = destinationFloor;
        displayLifts();
    }

    public static void assignNearestLiftOfSameDirection(int currentFloor, int destinationFloor){
        Lift nearestLift = new Lift("lift", Integer.MAX_VALUE);
        int minDiff = Math.abs(nearestLift.currFloor - currentFloor);
        boolean preferredDirection = (currentFloor - destinationFloor) < 0; // true for down && false for up
        for(Lift lift : lifts){
            if(!lift.restrictedFloors[currentFloor] && !lift.restrictedFloors[destinationFloor]){
                int diff = Math.abs(lift.currFloor - currentFloor);
                if(minDiff > diff){  
                    nearestLift = lift;
                    minDiff = diff;
                } else if(minDiff == diff){
                    if(preferredDirection && lift.currFloor < nearestLift.currFloor) nearestLift = lift;
                    else if(!preferredDirection && lift.currFloor > nearestLift.currFloor)    nearestLift = lift;
                }
            }
        }
        if(nearestLift.name.equals("lift")) System.out.println("Error! Cannot assign a lift");
        else    System.out.println(nearestLift.name + " is assigned");
        nearestLift.currFloor = destinationFloor;
        displayLifts();
    }

    public static void restrictLifts(int index, int start, int end){
        Lift lift = lifts[index];
        for(int i = 1; i <= floors; i++){
            if(i < start || i > end)    lift.restrictedFloors[i] = true;
        }
    }

    public static int findNoOfStops(Lift lift, int currentFloor, int destinationFloor){
        int stops = 0;
        for(int i = lift.currFloor + 1; i < currentFloor; i++){
            if(!lift.restrictedFloors[i])   stops++;
        }
        for(int i = currentFloor; i <= destinationFloor; i++){
            if(!lift.restrictedFloors[i])   stops++;
        }
        return stops;
    }
}

class Lift{
    String name;
    int currFloor;
    boolean [] restrictedFloors;
    Lift(String name, int currFloor){
        this.name = name;
        this.currFloor = currFloor;
        restrictedFloors = new boolean[Main.floors + 1];
    }

    @Override
    public String toString(){
        return name + " - " + currFloor;
    }
}
