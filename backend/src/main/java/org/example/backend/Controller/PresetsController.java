package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.Presets;
import org.example.backend.Domains.Settings;
import org.example.backend.Service.PresetsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presets")
@AllArgsConstructor
public class PresetsController {

    private PresetsService presetsService;


//    get settings
    @GetMapping("/{settingsId}/settings")
    public ResponseEntity<?> getSettings(@PathVariable Long settingsId) {
        return new ResponseEntity<>(presetsService.getSettings(settingsId), HttpStatus.OK);
    }

//    delete settings
    @DeleteMapping("/{presetID}/delete")
    public ResponseEntity<?> deletePreset(@PathVariable Long presetID) {
        presetsService.deleteSettings(presetID);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    update settings
//    Need to add a way to rename it within this
    @PutMapping("/{presetId}/update")
    public ResponseEntity<?> updatePreset(@PathVariable Long presetID, @RequestBody Settings settings, @RequestParam String presetName) {
        presetsService.updatePreset(presetID, settings, presetName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getPresets(@PathVariable Long token) {
        return new ResponseEntity<>(presetsService.getPresets(token), HttpStatus.OK);
    }


    @PostMapping("{token}/preset")
    ResponseEntity<?> createPreset(@RequestBody Presets preset, @PathVariable Long token) {
        presetsService.createPreset(preset, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
