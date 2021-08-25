package charper.advent19.day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayTwelve {
    private static final int ITERS = 1000;
    private Moon[] moons = {
        new Moon(-8, -18, 6),
        new Moon(-11, -14, 4),
        new Moon(8, -3, -10),
        new Moon(-2, -16, 1)
    };

    private OneDimMoon[] xMoons = {
        new OneDimMoon(-8),
        new OneDimMoon(-11),
        new OneDimMoon(8),
        new OneDimMoon(-2)
    };

    private OneDimMoon[] yMoons = {
        new OneDimMoon(-18),
        new OneDimMoon(-14),
        new OneDimMoon(-3),
        new OneDimMoon(-16)
    };

    private OneDimMoon[] zMoons = {
        new OneDimMoon(6),
        new OneDimMoon(4),
        new OneDimMoon(-10),
        new OneDimMoon(1)
    };

    public DayTwelve() {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private long partTwo() {
        long xiters = findRepeat(xMoons);
        long yiters = findRepeat(yMoons);
        long ziters = findRepeat(zMoons);
        return crappyLcm(xiters, yiters, ziters);
    }

    private long crappyLcm(long a, long b, long c) {
        long multiplier = Math.max(Math.max(a, b), c);
        long lcm = multiplier;
        while(lcm % a != 0 || lcm % b != 0 || lcm % c != 0) {
            lcm += multiplier;
        }
        return lcm;
    }

    private long findRepeat(OneDimMoon[] moons) {
        long iter = 0;
        Set<List<Long>> previous = new HashSet<>();
        previous.add(getState(moons));
        while (true) {
            performStep(moons);
            iter += 1;
            List<Long> currentState = getState(moons);
            if (previous.contains(currentState)) {
                break;
            }
            previous.add(currentState);
        }
        return iter;
    }

    private List<Long> getState(OneDimMoon[] moons) {
        List<Long> state = new ArrayList<>();
        for (OneDimMoon m : moons) {
            state.add(m.getPos());
            state.add(m.getVel());
        }
        return state;
    }

    private void performStep(OneDimMoon[] moons) {
        for (int i = 0; i < moons.length + 1; i++) {
            for (int j = i + 1; j < moons.length; j++) {
                moons[i].applyGravity(moons[j]);
            }
        }
        for (OneDimMoon moon: moons) {
            moon.applyVelocity();
        }
    }

    private long partOne() {
        for (int iters = 0; iters < ITERS; iters ++) {
            for (int i = 0; i < moons.length + 1; i++) {
                for (int j = i + 1; j < moons.length; j++) {
                    moons[i].applyGravity(moons[j]);
                }
            }
            for (Moon moon: moons) {
                moon.applyVelocity();
            }
        }
        long energy = 0;
        for (Moon moon: moons) {
            energy += moon.getTotalEnergy();
        }
        return energy;
    }

    public static class OneDimMoon {
        private long pos;
        private long vel = 0;

        public OneDimMoon(long pos) {
            this.pos = pos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof OneDimMoon)) {
                return false;
            }
            OneDimMoon oneDimMoon = (OneDimMoon) o;
            return pos == oneDimMoon.getPos() && vel == oneDimMoon.getVel();
        }

        @Override
        public int hashCode() {
            int hashcode = 7;
            hashcode = 31 * hashcode + (int) pos;
            hashcode = 31 * hashcode + (int) vel;
            return hashcode;
        }

        public void applyVelocity() {
            pos += vel;
        }

        // This will apply to both moons.
        public void applyGravity(OneDimMoon other) {
            if (other.getPos() > pos) {
                vel += 1;
                other.setVel(other.getVel() - 1);
            } else if (other.getPos() < pos) {
                vel -= 1;
                other.setVel(other.getVel() + 1);
            }
        }

        public long getPos() {
            return pos;
        }

        public void setPos(long pos) {
            this.pos = pos;
        }

        public long getVel() {
            return vel;
        }

        public void setVel(long vel) {
            this.vel = vel;
        }
    }

    public static class Moon {
        private long xPos;
        private long yPos;
        private long zPos;
        private long xVel = 0;
        private long yVel = 0;
        private long zVel = 0;

        public Moon(long xPos, long yPos, long zPos) {
            this.setXPos(xPos);
            this.setYPos(yPos);
            this.setZPos(zPos);
        }

        public long getTotalEnergy() {
            return getKineticEnergy() * getPotentialEnergy();
        }

        public long getPotentialEnergy() {
            return Math.abs(xPos) + Math.abs(yPos) + Math.abs(zPos);
        }

        public long getKineticEnergy() {
            return Math.abs(xVel) + Math.abs(yVel) + Math.abs(zVel);
        }

        public void applyVelocity() {
            xPos += xVel;
            yPos += yVel;
            zPos += zVel;
        }

        // This will apply to both moons.
        public void applyGravity(Moon other) {
            if (other.getXPos() > xPos) {
                xVel += 1;
                other.setXVel(other.getXVel() - 1);
            } else if (other.getXPos() < xPos) {
                xVel -= 1;
                other.setXVel(other.getXVel() + 1);
            }

            if (other.getYPos() > yPos) {
                yVel += 1;
                other.setYVel(other.getYVel() - 1);
            } else if (other.getYPos() < yPos) {
                yVel -= 1;
                other.setYVel(other.getYVel() + 1);
            }

            if (other.getZPos() > zPos) {
                zVel += 1;
                other.setZVel(other.getZVel() - 1);
            } else if (other.getZPos() < zPos) {
                zVel -= 1;
                other.setZVel(other.getZVel() + 1);
            }
        }

        // Getter and Setters

        public long getXPos() {
            return xPos;
        }

        public void setXPos(long xPos) {
            this.xPos = xPos;
        }

        public long getYPos() {
            return yPos;
        }

        public void setYPos(long yPos) {
            this.yPos = yPos;
        }

        public long getZPos() {
            return zPos;
        }

        public void setZPos(long zPos) {
            this.zPos = zPos;
        }

        public long getXVel() {
            return xVel;
        }

        public void setXVel(long xVel) {
            this.xVel = xVel;
        }

        public long getYVel() {
            return yVel;
        }

        public void setYVel(long yVel) {
            this.yVel = yVel;
        }

        public long getZVel() {
            return zVel;
        }

        public void setZVel(long zVel) {
            this.zVel = zVel;
        }

    }

}