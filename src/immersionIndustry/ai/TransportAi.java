package immersionIndustry.ai;

import mindustry.ai.types.*;
import arc.math.*;
import mindustry.ai.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class TransportAi extends AIController {
  
  Building from,to;
  boolean bool;
  
  public TransportAi(Building from,Building to) {
    this.from = from;
    this.to = to;
    target = to;
  }
  
  @Override
  public void updateMovement() {
    
    if(from != null && to != null) {
      if(unit.inRange(build)) {
        if(bool) {
          int i = to.get(to.first());
          if(i > 0) {
            unit.stack.set(to.first(),to.removeStack(to.first(),i));
            returnToBase();
          }
        }else {
          int i = to.acceptStack(unit.stack.item,unit.stack.amount,unit);
          if(i > 0) {
            to.handleStack(unit.stack.item,unit.stack.amount,unit);
            returnToBase();
          }
        }
      }else {
        unit.movePref(vec.trns(unit.angleTo(to.x, to.y), unit.speed()));
      }
    }
    
    faceTarget();
  }
  
  public void returnToBase() {
    bool = !bool;
    Building build = from;
    to = from;
    from = build;
  }
  
}