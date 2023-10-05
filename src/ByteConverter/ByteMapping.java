package ByteConverter;

import java.nio.ByteBuffer;
import java.util.List;

public class ByteMapping {
    // Current iteration state
    private boolean currentIterationState = false;
    private int iterator = 0;

    public List<Byte> optimizationByte(List<Byte> byteStorage) {
        return byteStorage.stream()
                .filter(b -> b != 33)
                .filter(b -> b != 44)
                .filter(b -> b != 46)
                .filter(b -> b != 10)
                .toList();
    }
    public void toLetter(ByteBuffer buffer, StringBuilder wordPicker, List<String> wordsStorage) {
        for (; iterator < buffer.limit(); iterator++) {
            // get value from buffer
            byte value = buffer.get(iterator);
            // if the field is 0 then we got all the values
            if (value == 0) {
                this.setCurrentIterationState(true);
                // increase the loop variable so that space does not iterate
                iterator++;
                wordsStorage.add(wordPicker.toString());
                int wordPickerLength = wordPicker.length();
                // clear the collected word
                wordPicker.delete(0, wordPickerLength);
                break;
            }
            wordPicker.append((char) value);
        }
    }

    public boolean getCurrentIterationState() {
        return currentIterationState;
    }

    public void setCurrentIterationState(boolean currentIterationState) {
        this.currentIterationState = currentIterationState;
    }
}
