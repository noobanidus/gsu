package noobanidus.mods.gsu.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import noobanidus.mods.gsu.init.ModAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NeutralMob.class)
public interface MixinNeutralMob {
  @Shadow void setRemainingPersistentAngerTime(int p_21673_);

  @Inject(method = "updatePersistentAnger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/NeutralMob;stopBeingAngry()V"), cancellable = true)
  default void GSUKeepAnger(ServerLevel p_21667_, boolean p_21668_, CallbackInfo ci) {
    if (this instanceof IAttachmentHolder holder) {
      if (holder.getData(ModAttachments.PLAYER_HOSTILE)) {

        ci.cancel();
      }
    }
  }

  @Inject(method="playerDied", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/NeutralMob;stopBeingAngry()V"), cancellable = true)
  default void GSUKeepAngerWhenPlayerDies(CallbackInfo ci) {
    if (this instanceof IAttachmentHolder holder) {
      if (holder.getData(ModAttachments.PLAYER_HOSTILE)) {
        setRemainingPersistentAngerTime(Integer.MAX_VALUE);
        ci.cancel();
      }
    }
  }

  @Inject(method="forgetCurrentTargetAndRefreshUniversalAnger", at=@At(value="HEAD"), cancellable = true)
  default void GSUPersistAnger (CallbackInfo ci) {
    if (this instanceof IAttachmentHolder holder) {
      if (holder.getData(ModAttachments.PLAYER_HOSTILE)) {
        setRemainingPersistentAngerTime(Integer.MAX_VALUE);
        ci.cancel();
      }
    }
  }

  @Inject(method="isAngry", at=@At(value="HEAD"), cancellable = true)
  default void GSUIsAngry (CallbackInfoReturnable<Boolean> cir) {
    if (this instanceof IAttachmentHolder holder) {
      if (holder.getData(ModAttachments.PLAYER_HOSTILE)) {
        setRemainingPersistentAngerTime(Integer.MAX_VALUE);
        cir.setReturnValue(true);
        cir.cancel();
      }
    }
  }

  @Inject(method="isAngryAt", at=@At(value="HEAD"), cancellable = true)
  default void GSUIsAngryAt (LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
    if (this instanceof IAttachmentHolder holder && target instanceof Player) {
      if (holder.getData(ModAttachments.PLAYER_HOSTILE)) {
        setRemainingPersistentAngerTime(Integer.MAX_VALUE);
        cir.setReturnValue(true);
        cir.cancel();
      }
    }
  }
}
