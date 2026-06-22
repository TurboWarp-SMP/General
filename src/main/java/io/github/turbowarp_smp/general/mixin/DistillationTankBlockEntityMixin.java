package io.github.turbowarp_smp.general.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = { "com.jesz.createdieselgenerators.content.distillation.DistillationTankBlockEntity" }, remap = false)
public abstract class DistillationTankBlockEntityMixin {
  @Shadow
  protected int width;
  @Shadow
  int processingTime;

  @Unique
  private int general$getTowerAreaMultiplier() {
    int safeWidth = Math.max(1, this.width);
    return safeWidth * safeWidth;
  }

  @Inject(method = { "startProcessing" }, at = { @At("TAIL") }, remap = false)
  private void general$scaleDistillationProcessingTime(CallbackInfo ci) {
    int multiplier = this.general$getTowerAreaMultiplier();
    if (multiplier > 1 && this.processingTime > 1) {
      this.processingTime = Math.max(1, (int) Math.ceil((double) this.processingTime / (double) multiplier));
    }
  }
}
