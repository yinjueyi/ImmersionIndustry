package immersionIndustry.contents;
import arc.*;
import arc.math.*;
import arc.math.geom.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ctype.*;
import mindustry.content.*;

import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.experimental.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import immersionIndustry.IMColors;

public class IMFx implements ContentList {
  public static Effect dispersion,shockWave,absorbedEnergy;
  
  @Override
  public void load() {
    dispersion = new Effect(60f,e -> {
      color(IMColors.colorPrimary,IMColors.colorDarkPrimary,e.fout());
      randLenVectors(e.id, 4, 1f + 20f * e.fout(), e.rotation, 64, (x, y) -> {
        lineAngle(e.x - x, e.y - y, Mathf.angle(x, y), e.fslope() * 3f + 1f);
      });
    });
    
    absorbedEnergy = new Effect(80f, e -> {
      Draw.z(Layer.effect);
      color(IMColors.colorPrimary,IMColors.colorDarkPrimary,e.fout());
      if(e.data instanceof Building entity) {
        Vec2 vec = new Vec2(e.x,e.y);
        vec.lerp(entity.x, entity.y, Interp.sineIn.apply(e.fin()));
        Fill.circle(e.x, e.y, 2 * e.fslope());
        Draw.color();
        Fill.circle(e.x, e.y, 1 * e.fslope());
        Draw.alpha(e.fslope());
        Drawf.laser(entity.team,Core.atlas.find("parallax-laser"),Core.atlas.find("parallax-laser-end"),vec.x,vec.y,e.x,e.y,0.4f);
      }
    });
    
    shockWave = new Effect(60f,e -> {
      color(IMColors.colorPrimary,Color.white,e.fin());
      Lines.circle(e.x,e.y,64*e.fin());
      Lines.circle(e.x,e.y,46*e.fin());
      Lines.circle(e.x,e.y,32*e.fin());
      randLenVectors(e.id, 12, 7f + e.fin() * 13f, (x, y) -> {
        Fill.square(e.x + x, e.y + y, e.fout() * 2.1f + 0.5f, 45);
      });
    });
  }
  
}