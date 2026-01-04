package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.ArkColors;
import org.example.backend.Repo.ArkColorsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ColorsService {

    private final ArkColorsRepo arkColorsRepo;

    public ArkColors getColor(long colorId) {
        return arkColorsRepo.getArkColorsByColorId(colorId);
    }

    public void createColor(ArkColors arkColors) {
        arkColorsRepo.save(arkColors);
    }

    public void updateColor(ArkColors arkColors, long colorId) throws Exception {
        ArkColors oldColor = arkColorsRepo.getArkColorsByColorId(colorId);

        if (oldColor == null) {
            throw new RuntimeException("Color with id " + colorId + " not found");
        }
        oldColor.setColor(arkColors.getColor());
        arkColorsRepo.save(oldColor);
    }

    public void deleteColor(long colorId) {
        arkColorsRepo.deleteArkColorsByColorId(colorId);
    }

    public List<ArkColors> getAllColors() {
        return arkColorsRepo.findAll();
    }

    public void createColorList(List<ArkColors> arkColors) {
        arkColorsRepo.saveAll(arkColors);
    }

}
