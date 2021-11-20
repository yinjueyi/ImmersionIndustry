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
  
  Building from;
  
  public TransportAi(Building from,Building target) {
    this.from = from;
    this.target = target;
  }
  
  @Override
  public void updateMovement() {
    
    if(from != null && target != null) {
      unit.movePref(vec.trns(unit.angleTo(target.x, target.y), unit.speed()));
    }
    
    faceTarget();
  }
  
}
