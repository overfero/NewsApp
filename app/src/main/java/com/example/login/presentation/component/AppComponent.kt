package com.example.login.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.login.presentation.component.NavigationItem
import com.example.login.util.Screen
import com.example.login.ui.theme.BackgroundColor
import com.example.login.ui.theme.DescColor
import com.example.login.ui.theme.IconColor
import com.example.login.ui.theme.Primary
import com.example.login.ui.theme.Secondary
import com.example.login.ui.theme.TextColor

@Composable
fun TextComponent(normalText: String) {
    Text(
        text = normalText,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeaderTextComponent(headerText: String){
    Text(
        text = headerText,
        modifier = Modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextFieldComponent(
    labelText: String, icon: ImageVector, value: String, error: Boolean = true, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        label = { Text(text = labelText)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = BackgroundColor
        ), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = null)
        }, maxLines = 1, singleLine = true, isError = !error
    )
}

@Composable
fun PasswordFieldComponent(value: String, error: Boolean = true, onValueChange: (String) -> Unit) {
    var visible by remember{ mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        label = { Text(text = "Password")},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = BackgroundColor
        ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions { localFocusManager.clearFocus() },maxLines = 1, singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
        }, trailingIcon = {
            val iconImage = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (visible) "Hide Password" else "Show Password"

            IconButton(onClick = { visible = !visible}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        }, visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !error
    )
}

@Composable
fun CheckboxComponent(navHostController: NavHostController, value: Boolean, onCheckChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = value, onCheckedChange = {onCheckChanged.invoke(it)})
        ClickableTextComponent(navHostController)
    }
}

@Composable
fun ClickableTextComponent(navHostController: NavHostController) {
    val annotatedString = buildAnnotatedString {
        append("By continuing you accept our ")
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation("privacy_policy", annotation = "Privacy Policy")
            append("Privacy Policy")
        }
        append(" and ")
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation("term_of_use", annotation = "Term of Use")
            append("Term of Use")
        }
    }

    ClickableText(text = annotatedString){
        annotatedString.getStringAnnotations(it,it)
            .firstOrNull()?.also {span ->
                when(span.tag){
                    "privacy_policy" -> {navHostController.navigate(Screen.PrivacyPolicy.route)}
                    "term_of_use" -> {navHostController.navigate(Screen.TermOfUse.route)}
                }
            }
    }
}

@Composable
fun ButtonComponent(text: String, enabled: Boolean = true, onButtonClicked: () -> Unit) {
    Button(
        onClick = { onButtonClicked.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = enabled
        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(50.dp)
                ), contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
                )
        }
    }
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
        
        Text(text = "or", fontSize = 14.sp, color = TextColor, modifier = Modifier.padding(8.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginComponent(normalText: String, annotText: String, navHostController: NavHostController) {
    val annotatedString = buildAnnotatedString {
        append(normalText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(annotText.lowercase(), annotation = annotText)
            append(annotText)
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
    ){
        annotatedString.getStringAnnotations(it,it)
            .firstOrNull()?.also {span ->
                when(span.tag){
                    "login" -> {navHostController.navigate(Screen.Login.route)}
                    "register" -> {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.SignUp.route)
                    }
                }
            }
    }
}

@Composable
fun UnderlinedTextComponent(normalText: String) {
    Text(
        text = normalText,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = TextColor,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onNotificationClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "News",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
                },
        actions = {
            IconButton(onClick = { onNotificationClick.invoke() }) {
                Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = "Notifications", tint = Color.Black)
            }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
}

@Composable
fun SmallDetailText(category: String, time: String, modifier: Modifier = Modifier) {
    Row(modifier = Modifier) {
        Text(
            text = category,
            modifier = modifier,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ), color = Color.DarkGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = time,
            modifier = modifier,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ), color = Color.Black
        )
    }
}

@Composable
fun HeaderNewsItem(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal
        ), color = Color.Black
    )

}

@Composable
fun DescriptionNewsItem(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = DescColor,
        maxLines = 3, overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CategoryTag(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(Color.Gray)
            .padding(vertical = 2.dp, horizontal = 6.dp)
    )
}

@Composable
fun DetailTextSmall(source: String, time: String, modifier: Modifier = Modifier) {
    Row(modifier = Modifier) {
        Text(
            text = source,
            modifier = modifier,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ), color = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = time,
            modifier = modifier,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ), color = Color.White
        )
    }
}

@Composable
fun HeaderNewsDetail(text: String, fontSize: Int, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal
        ), color = Color.White
    )

}

@Composable
fun DescriptionNewsDetail(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = Color.Black
    )
}

@Composable
fun CategoryItem(
    title: String,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 4.dp)
            .selectable(
                selected = selected,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onItemClick.invoke() }
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = if (selected) Color.DarkGray
        else Color.Transparent)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 8.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = if (selected) Color.White else Color.Black,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val items = listOf(
        NavigationItem(title = "Home", icon = Icons.Outlined.Home, selected = Icons.Filled.Home),
        NavigationItem(title = "Discover", icon = Icons.Outlined.Search, selected = Icons.Filled.Search),
        NavigationItem(title = "Bookmark", icon = Icons.Outlined.Bookmarks, selected = Icons.Filled.Bookmarks),
        NavigationItem(title = "Setting", icon = Icons.Outlined.Settings, selected = Icons.Filled.Settings)
    )
    var selected by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = Modifier.height(65.dp),
        containerColor = Color.White.copy(0.5f)
    ) {
        Row(
            modifier = Modifier.background(Color.White.copy(0.5f)),
            verticalAlignment = Alignment.Bottom
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    modifier = Modifier.background(Color.White.copy(0.5f)),
                    selected = selected == index,
                    onClick = {
                        selected = index
                        when(selected){
                            0 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.NewsList.route)
                            }
                            1 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Discover.route)
                            }
                            2 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Bookmark.route)
                            }
                            3 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Setting.route)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected == index) bottomItem.selected else bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = IconColor
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SearchDiscover(text: String, onValueChange: (String) -> Unit, submitSearch: () -> Unit) {
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text, 
        onValueChange = { onValueChange.invoke(it) },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Icon") },
        trailingIcon = {
            IconButton(onClick = { submitSearch.invoke() }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Search")
            }
        }, maxLines = 1, singleLine = true, shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Primary,
            unfocusedContainerColor = BackgroundColor,
            focusedContainerColor = BackgroundColor
        ), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            submitSearch.invoke()
            localFocusManager.clearFocus()
                                          },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun TextSearch(query: String) {
    Text(
        text = "You searched for '$query'",
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal
        ), color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun PrevComponent() {
    Surface() {
        TextSearch(query = "Gemini")
    }
}