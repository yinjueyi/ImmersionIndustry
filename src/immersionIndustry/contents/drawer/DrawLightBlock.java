package immersionIndustry.contents.drawer;

import mindustry.world.draw.*;
import arc.graphics.g2d.*;
import arc.Core;
import arc.math.*;
import arc.util.*;
import arc.graphics.Blending;
import mindustry.graphics.Layer;
import mindustry.graphics.Drawf;
import mindustry.graphics.Shaders;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import static mindustry.Vars.*;

import immersionIndustry.IMColors;

public class DrawLightBlock extends DrawBlock {
  
  public TextureRegion light;
  
  @Override
  public void draw(GenericCrafterBuild build){
    Draw.rect(build.block.region, build.x, build.y, build.block.rotate ? build.rotdeg() : 0);
    Draw.color(IMColors.colorPrimary,IMColors.colorDarkPrimary,build.warmup);
    Draw.z(Layer.effect);
    Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + build.progress * 2f, 0.3f + build.progress * 0.05f)) * build.warmup);
    Draw.blend(Blending.additive);
    Draw.draw(Layer.blockUnder, () -> {
      Shaders.build.region = light;
      Shaders.build.progress = build.progress;
      Shaders.build.color.set(Tmp.c1.set(IMColors.colorPrimary).lerp(IMColors.colorDarkPrimary, build.warmup));
      Shaders.build.color.a = build.warmup;
      Shaders.build.time = -build.totalProgress / 20f;
      
      Draw.shader(Shaders.build);
      Draw.rect(light, build.x, build.y, build.rotation);
      Draw.shader();
      
      Draw.color(IMColors.colorDarkPrimary,IMColors.colorPrimary,build.warmup);
      Draw.alpha(build.warmup);
      
      Lines.lineAngleCenter(build.x + Mathf.sin(build.totalProgress, 20f, tilesize / 2f * build.block.size - 2f), build.y, 90, build.block.size * tilesize - 4f);
      
      Draw.reset();
    });
    
    Draw.blend();
    Draw.reset();
  }
  
  @Override
  public void load(Block block) {
    light = Core.atlas.find(block.name + "-light");
  }
  
}
