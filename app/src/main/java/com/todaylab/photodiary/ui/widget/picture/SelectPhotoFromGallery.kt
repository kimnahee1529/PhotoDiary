package com.todaylab.photodiary.ui.widget.picture


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

class SelectPhotoFromGallery {
}

//@Composable
//fun DiaryPhotoAdder(
//    onUrisReady: (List<Uri>) -> Unit
//) {
//    GalleryImagePickerButton(
//        label = "사진 최대 10장 선택",
//        maxItems = 10
//    ) { uris ->
//        onUrisReady(uris) // <= 여기서 상태 저장/미리보기/업로드
//    }
//}
//
///**
// * 갤러리/포토피커에서 이미지를 선택하는 버튼 (멀티 선택 + 최대 개수 제한)
// *
// * @param maxItems 선택 가능한 최대 개수 (기본 10)
// * @param onPicked 선택된 이미지 URI 목록 (최대 maxItems개로 제한되어 반환)
// */
//@Composable
//fun GalleryImagePickerButton(
//    label: String = "사진 선택",
//    maxItems: Int = 10,
//    onPicked: (List<Uri>) -> Unit
//) {
//    // ---- Android 13+ : 시스템 Photo Picker (멀티 + 최대 개수 지정 가능) ----
//    val photoPickerMulti = rememberLauncherForActivityResult(
//        // androidx.activity 1.8.0+ 에서 maxItems 파라미터 제공
//        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems)
//    ) { uris: List<Uri> ->
//        onPicked(uris.take(maxItems))
//    }
//
//    // ---- Android 12- : ACTION_OPEN_DOCUMENT 기반 멀티 선택 ----
//    val openMultipleDocs = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments()
//    ) { uris: List<Uri> ->
//        onPicked(uris.take(maxItems))
//    }
//
//    Button(onClick = {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            photoPickerMulti.launch(
//                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//            )
//        } else {
//            // MIME 타입 배열 전달 (이미지 전용)
//            openMultipleDocs.launch(arrayOf("image/*"))
//        }
//    }) {
//        Text(text = label)
//    }
//}

/**
 * 빈 사진칸과 사진 미리보기를 포함한 사진 선택 컴포넌트
 * 16:9 비율로 가로를 꽉 채우며, 사진이 없을 때는 클릭 가능한 빈 사진칸을 표시
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun PhotoSelectorWithPreview(
    selectedPhotos: List<Uri>,
    onPhotosSelected: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val photoPickerMulti = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(10)
    ) { uris: List<Uri> ->
        onPhotosSelected(uris.take(10))
    }

    val openMultipleDocs = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        onPhotosSelected(uris.take(10))
    }

    fun launchPhotoPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            photoPickerMulti.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            openMultipleDocs.launch(arrayOf("image/*"))
        }
    }

    BoxWithConstraints( // ⬅️ 부모 너비로 슬롯 크기 결정
        modifier = modifier.fillMaxWidth()
    ) {
        val slotSize = maxWidth // 빈칸과 동일 크기(=부모 가로폭의 정사각형)

        Column(Modifier.fillMaxWidth()) {

            if (selectedPhotos.isEmpty()) {
                // 빈 슬롯: 부모 가로폭 x 부모 가로폭 (정사각형)
                EmptyPhotoSlot(
                    onClick = { launchPhotoPicker() },
                    modifier = Modifier
                        .size(slotSize)   // ⬅️ fillMaxWidth + aspectRatio 대신 동일 크기 보장
                )
            } else {
                // 선택된 사진: 빈 슬롯과 "완전히 동일"한 크기로 표시
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(slotSize) // ⬅️ 행 높이 = 슬롯 크기(정사각형 유지)
                ) {
                    items(selectedPhotos) { photoUri ->
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(photoUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = "선택된 사진",
                            contentScale = ContentScale.Crop, // ⬅️ 정사각형에 맞춰 확대/크롭
                            modifier = Modifier
                                .size(slotSize)   // ⬅️ 빈 슬롯과 같은 크기
                                .aspectRatio(1f)  // 안전하게 1:1 유지
                        )
                    }

                    // 사진 추가 버튼도 동일 크기
                    item {
                        AddMorePhotoSlot(
                            onClick = { launchPhotoPicker() },
                            modifier = Modifier
                                .size(slotSize)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 클릭 가능한 빈 사진칸 컴포넌트
 */
@Composable
private fun EmptyPhotoSlot(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "사진 추가",
                tint = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "사진을 선택하세요",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * 사진 추가 버튼 컴포넌트 (이미 사진이 있을 때 사용)
 */
@Composable
private fun AddMorePhotoSlot(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.Gray,
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "사진 추가",
                tint = Color.Gray,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "사진 추가",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPhotoSelectorWithPreview() {
    PhotoSelectorWithPreview(
        selectedPhotos = emptyList(),
        onPhotosSelected = { }
    )
}

@Preview
@Composable
private fun PreviewEmptyPhotoSlot() {
    EmptyPhotoSlot(
        onClick = { }
    )
}

@Preview
@Composable
private fun PreviewAddMorePhotoSlot() {
    AddMorePhotoSlot(
        onClick = { }
    )
}

