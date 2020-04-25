extends Control


onready var fb_ad = $GodotFacebookAd


func _on_Button_button_down():
	fb_ad.show_interstitial()
	

func _on_GodotFacebookAd_interstitial_dismissed():
	print("interstitial ad has been dismissed")
