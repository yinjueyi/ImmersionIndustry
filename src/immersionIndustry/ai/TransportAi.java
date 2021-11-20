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
  }
  
  @Override
  public void updateMovement() {
    if(from != null && to != null) {
      target = to;
    }
  }
  
}