package io.github.turbowarp_smp.general.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = { "com.jesz.createdieselgenerators.content.distillation.DistillationTankBlockEntity" }, remap = false)
public abstract class DistillationTankBlockEntityMixin {
  @Shadow
  protected int width;
  @Shadow
  int processingTime;

  @Inject(method = "startProcessing", at = @At("TAIL"), remap = false)
  private void general$scaleDistillationProcessingTime(CallbackInfo ci) {
    if (this.processingTime <= 1) {
      return;
    }

    int currentWidth = this.width > 0 ? this.width : 1;
    int multiplier = currentWidth * currentWidth;

    if (multiplier > 1) {
      this.processingTime = (this.processingTime + multiplier - 1) / multiplier;
    }
  }
}
