package immersionIndustry.ai;

import mindustry.ai.types.*;
import arc.math.*;
import mindustry.ai.*;
import mindustry.type.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

import immersionIndustry.contents.IMFx;
import immersionIndustry.IMColors;

public class TransportAi extends AIController {
  
  Building from,to;
  boolean wait;
  float waitMultiple = 0.06f;
  
  public TransportAi(Building from,Building to) {
    this.from = from;
    this.to = to;
    target = to;
  }
  
  @Override
  public void updateMovement() {
    if(from == null || to == null || wait) return;
    if(unit.stack.amount > 0) {
      //运送物品
      if(unit.inRange(to)) {
        int i = to.acceptStack(unit.stack.item,unit.stack.amount,unit);
        if(i > 0) {
          float waitTime = i < 600 ? 600 : i * waitMultiple;
          wait = true;
          IMFx.takeItemEffect(unit.x,unit.y,to.x,to.y,IMColors.colorYellow,waitTime);
          Time.run(waitTime,() -> {
            to.handleStack(unit.stack.item,i,unit);
            unit.stack.set(unit.stack.item,unit.stack.amount - i);
            wait = false;
          });
        }
      }else {
        unit.movePref(vec.trns(unit.angleTo(to.x, to.y), unit.speed()));
      }
    }else {
      Item item = from.items.first();
      if(unit.inRange(from) && item != null) {
        float waitTime = i < 600 ? 600 : unit.stack.amount * waitMultiple;
        wait = true;
        IMFx.takeItemEffect(from.x,from.y,unit.x,unit.y,IMColors.colorPrimary,waitTime);
        Time.run(waitTime,() -> {
          int i = from.removeStack(item,unit.type.itemCapacity);
          unit.stack.set(item,i);
          wait = false;
        });
      }else {
        unit.movePref(vec.trns(unit.angleTo(from.x, from.y), unit.speed()));
      }
    }
    
    faceTarget();
  }
  
}