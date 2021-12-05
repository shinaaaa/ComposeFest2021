package com.example.layoutsinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.layoutsinjetpackcompose.ui.theme.Layouts_in_Jetpack_ComposeTheme

class ConstraintLayoutActivitiy : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecoupledConstraintLayout()
//            ConstraintLayoutContent()
//            LargeConstraintLayout()
        }
    }
}

@Composable
private fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })

        val barrier = createEndBarrier(button1, text)
        Button(onClick = {}, modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(barrier)
        }) {
            Text("Button 2")
        }
    }
}

@Composable
private fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a vary vary vary vary vary vary vary vary vary long text.",
            modifier = Modifier.constrainAs(text) {
                linkTo(guideline, parent.end)
                width = Dimension.preferredWrapContent
            })

    }
}

@Composable
private fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp)
        } else {
            decoupledConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    Layouts_in_Jetpack_ComposeTheme() {
        DecoupledConstraintLayout()
//        ConstraintLayoutContent()
//        LargeConstraintLayout()
    }
}