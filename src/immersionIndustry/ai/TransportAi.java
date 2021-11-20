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
  
  public TransportAi(Building from,Building to) {
    this.from = from;
    this.to = to;
    target = to;
  }
  
  @Override
  public void updateMovement() {
    if(from == null || to == null) return;
    if(unit.stack.amount > 0) {
      //运送物品
      if(unit.inRange(to)) {
        int i = to.acceptStack(unit.stack.item,unit.stack.amount,unit);
        if(i > 0) {
          to.handleStack(unit.stack.item,unit.stack.amount,unit);
        }
      }else {
        unit.movePref(vec.trns(unit.angleTo(to.x, to.y), unit.speed()));
      }
    }else {
      if(unit.inRange(from)) {
        int i = from.removeStack(from.items.first(),unit.type.itemCapacity);
        unit.stack.set(from.items.first(),i);
      }else {
        unit.movePref(vec.trns(unit.angleTo(from.x, from.y), unit.speed()));
      }
    }
    
    faceTarget();
  }
  
}