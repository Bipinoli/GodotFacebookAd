extends Node

class_name GodotFacebookAd, "res://facebookAd-lib/facebookAudienceNetwork.jpeg"

# signals

signal interstitial_loaded
signal interstitial_displayed
signal interstitial_dismissed
signal interstitial_error(adError)
signal interstitial_clicked
signal interstitial_impression_logged

signal rewarded_video_loaded
signal rewarded_video_displayed
signal rewarded_video_closed
signal rewarded_video_error(adError)
signal rewarded_video_clicked
signal rewarded_video_impression_logged
signal rewarded_video_completed


export var interstitialAdId = ""
export var rewardedVideoAdId = ""


var _fb_singleton = null



func _enter_tree():
	if not init():
		print("-- GodotFacebookAd Java Singleton not found")


# initialization
func init() -> bool:
	if(Engine.has_singleton("GodotFacebookAd")):
		_fb_singleton = Engine.get_singleton("GodotFacebookAd")
		_fb_singleton.init(get_instance_id(), interstitialAdId, rewardedVideoAdId)
		return true
	return false


# showing ads

func show_interstitial() -> void:
	if _fb_singleton != null:
		_fb_singleton.showInterstitial()


func show_rewarded_video() -> void:
	if _fb_singleton != null:
		_fb_singleton.showRewardedVideo()



# callbacks


# interstitials 

func _on_interstitial_loaded():
	emit_signal("interstitial_loaded")

func _on_interstitial_dismissed():
	emit_signal("interstitial_dismissed")

func _on_interstitial_displayed():
	emit_signal("interstitial_displayed")

func _on_interstitial_error(adError):
	emit_signal("interstitial_error", adError)

func _on_interstitial_clicked():
	emit_signal("interstitial_clicked")

func _on_interstitial_impression_logged():
	emit_signal("interstitial_impression_logged")


# rewarded video

func _on_rewarded_video_loaded():
	emit_signal("rewarded_video_loaded")

func _on_rewarded_video_closed():
	emit_signal("rewarded_video_closed")

func _on_rewarded_video_displayed():
	emit_signal("rewarded_video_displayed")

func _on_rewarded_video_error(adError):
	emit_signal("rewarded_video_error", adError)

func _on_rewarded_video_clicked():
	emit_signal("rewarded_video_clicked")

func _on_rewarded_video_impression_logged():
	emit_signal("rewarded_video_impression_logged")

func _on_rewarded_video_complete():
	emit_signal("rewarded_video_completed")

