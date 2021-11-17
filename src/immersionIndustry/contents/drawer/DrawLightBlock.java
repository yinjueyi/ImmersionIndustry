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
    Draw.color(IMColors.colorPrimary,IMColors.colorDarkPrimary,build.warmup);
    Draw.z(Layer.effect);
    Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + build.progress * 2f, 0.3f + build.progress * 0.05f)) * build.warmup);
    
    Draw.rect(light, build.x, build.y, build.rotation);
    
    Draw.alpha(build.warmup);
    Draw.color(IMColors.colorDarkPrimary,IMColors.colorPrimary,build.warmup);
    
    Lines.lineAngleCenter(build.x + Mathf.sin(build.totalProgress, 20f, tilesize / 2f * build.block.size - 2f), build.y, 90, build.block.size * tilesize - 4f);
    
    Draw.reset();
    
    Draw.rect(build.block.region, build.x, build.y, build.block.rotate ? build.rotdeg() : 0);
  }
  
  @Override
  public void load(Block block) {
    light = Core.atlas.find(block.name + "-light");
  }
  
}