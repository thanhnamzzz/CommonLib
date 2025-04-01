package io.virgo_common.common_libs.customView.materialEdittext

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import io.virgo_common.common_libs.R
import io.virgo_common.common_libs.customView.materialEdittext.Colors.isLight
import io.virgo_common.common_libs.customView.materialEdittext.Density.dp2px
import io.virgo_common.common_libs.customView.materialEdittext.enums.FloatingLabelType
import io.virgo_common.common_libs.customView.materialEdittext.enums.FloatingLabelType.Companion.fromInt
import io.virgo_common.common_libs.customView.materialEdittext.validation.METLengthChecker
import io.virgo_common.common_libs.customView.materialEdittext.validation.METValidator
import kotlin.math.max
import androidx.core.graphics.toColorInt
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import androidx.core.graphics.withSave

/**
 * AutoCompleteTextView in Material Design
 *
 *
 * author:rengwuxian
 *
 *
 */
class MaterialAutoCompleteTextViewK : AppCompatAutoCompleteTextView {
	/**
	 * the spacing between the main text and the inner top padding.
	 */
	private var extraPaddingTop = 0

	/**
	 * the spacing between the main text and the inner bottom padding.
	 */
	private var extraPaddingBottom = 0

	/**
	 * the extra spacing between the main text and the left, actually for the left icon.
	 */
	private var extraPaddingLeft = 0

	/**
	 * the extra spacing between the main text and the right, actually for the right icon.
	 */
	private var extraPaddingRight = 0

	/**
	 * the floating label's text size.
	 */
	private var floatingLabelTextSize = 0

	/**
	 * the floating label's text color.
	 */
	private var floatingLabelTextColor = 0

	/**
	 * the bottom texts' size.
	 */
	private var bottomTextSize = 0

	/**
	 * the spacing between the main text and the floating label.
	 */
	private var floatingLabelPadding = 0

	/**
	 * the spacing between the main text and the bottom components (bottom ellipsis, helper/error text, characters counter).
	 */
	private var bottomSpacing = 0

	/**
	 * whether the floating label should be shown. default is false.
	 */
	private var floatingLabelEnabled = false

	/**
	 * whether to highlight the floating label's text color when focused (with the main color). default is true.
	 */
	private var highlightFloatingLabel = false

	/**
	 * the base color of the line and the texts. default is black.
	 */
	private var baseColor = 0

	/**
	 * get inner top padding, not the real paddingTop
	 */
	/**
	 * inner top padding
	 */
	var innerPaddingTop: Int = 0
		private set

	/**
	 * get inner bottom padding, not the real paddingBottom
	 */
	/**
	 * inner bottom padding
	 */
	var innerPaddingBottom: Int = 0
		private set

	/**
	 * get inner left padding, not the real paddingLeft
	 */
	/**
	 * inner left padding
	 */
	var innerPaddingLeft: Int = 0
		private set

	/**
	 * get inner right padding, not the real paddingRight
	 */
	/**
	 * inner right padding
	 */
	var innerPaddingRight: Int = 0
		private set

	/**
	 * the underline's highlight color, and the highlight color of the floating label if app:highlightFloatingLabel is set true in the xml. default is black(when app:darkTheme is false) or white(when app:darkTheme is true)
	 */
	private var primaryColor = 0

	/**
	 * the color for when something is wrong.(e.g. exceeding max characters)
	 */
	private var errorColor = 0

	/**
	 * min characters count limit. 0 means no limit. default is 0. NOTE: the character counter will increase the View's height.
	 */
	private var minCharacters = 0

	/**
	 * max characters count limit. 0 means no limit. default is 0. NOTE: the character counter will increase the View's height.
	 */
	private var maxCharacters = 0

	/**
	 * whether to show the bottom ellipsis in singleLine mode. default is false. NOTE: the bottom ellipsis will increase the View's height.
	 */
	private var singleLineEllipsis = false

	/**
	 * Always show the floating label, instead of animating it in/out. False by default.
	 */
	private var floatingLabelAlwaysShown = false

	/**
	 * Always show the helper text, no matter if the edit text is focused. False by default.
	 */
	private var helperTextAlwaysShown = false

	/**
	 * bottom ellipsis's height
	 */
	private var bottomEllipsisSize = 0

	/**
	 * min bottom lines count.
	 */
	private var minBottomLines = 0

	/**
	 * reserved bottom text lines count, no matter if there is some helper/error text.
	 */
	private var minBottomTextLines = 0

	/**
	 * real-time bottom lines count. used for bottom extending/collapsing animation.
	 */
	private var currentBottomLines = 0f

	/**
	 * bottom lines count.
	 */
	private var bottomLines = 0f

	/**
	 * Helper text at the bottom
	 */
	private var helperText: String? = null

	/**
	 * Helper text color
	 */
	private var helperTextColor = -1

	/**
	 * error text for manually invoked [.setError]
	 */
	private var tempErrorText: String? = null

	/**
	 * animation fraction of the floating label (0 as totally hidden).
	 */
	private var floatingLabelFraction = 0f

	/**
	 * whether the floating label is being shown.
	 */
	private var floatingLabelShown = false

	/**
	 * the floating label's focusFraction
	 */
	private var focusFraction = 0f

	/**
	 * The font used for the accent texts (floating label, error/helper text, character counter, etc.)
	 */
	private var accentTypeface: Typeface? = null

	/**
	 * Text for the floatLabel if different from the hint
	 */
	private var floatingLabelText: CharSequence? = null

	/**
	 * Whether or not to show the underline. Shown by default
	 */
	private var hideUnderline = false

	/**
	 * Underline's color
	 */
	private var underlineColor = 0

	/**
	 * Whether to validate as soon as the text has changed. False by default
	 */
	private var autoValidate = false

	/**
	 * Whether the characters count is valid
	 */
	var isCharactersCountValid: Boolean = false
		private set

	/**
	 * Whether use animation to show/hide the floating label.
	 */
	var isFloatingLabelAnimating: Boolean = false

	/**
	 * Whether check the characters count at the beginning it's shown.
	 */
	private var checkCharactersCountAtBeginning = false

	/**
	 * Left Icon
	 */
	private var iconLeftBitmaps: Array<Bitmap?>? = null

	/**
	 * Right Icon
	 */
	private var iconRightBitmaps: Array<Bitmap?>? = null

	/**
	 * Clear Button
	 */
	private var clearButtonBitmaps: Array<Bitmap?>? = null

	/**
	 * Auto validate when focus lost.
	 */
	var isValidateOnFocusLost: Boolean = false

	private var showClearButton = false
	private var firstShown = false
	private var iconSize = 0
	private var iconOuterWidth = 0
	private var iconOuterHeight = 0
	private var iconPadding = 0
	private var clearButtonTouched = false
	private var clearButtonClicking = false
	private var textColorStateList: ColorStateList? = null
	private var textColorHintStateList: ColorStateList? = null
	private val focusEvaluator = ArgbEvaluator()
	var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
	var textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
	var textLayout: StaticLayout? = null
	var labelAnimator: ObjectAnimator? = null
		get() {
			if (field == null) {
				field = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
			}
			field!!.duration = (if (this.isFloatingLabelAnimating) 300 else 0).toLong()
			return field
		}
	var labelFocusAnimator: ObjectAnimator? = null
		get() {
			if (field == null) {
				field = ObjectAnimator.ofFloat(this, "focusFraction", 0f, 1f)
			}
			return field
		}
	var bottomLinesAnimator: ObjectAnimator? = null
	var innerFocusChangeListener: OnFocusChangeListener? = null
	var outerFocusChangeListener: OnFocusChangeListener? = null
	var validators: MutableList<METValidator>? = null
		private set
	private var lengthChecker: METLengthChecker? = null

	constructor(context: Context) : super(context) {
		init(context, null)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		init(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
		init(context, attrs)
	}

	private fun init(context: Context, attrs: AttributeSet?) {
		iconSize = getPixel(32)
		iconOuterWidth = getPixel(48)
		iconOuterHeight = getPixel(32)

		bottomSpacing = resources.getDimensionPixelSize(R.dimen.inner_components_spacing)
		bottomEllipsisSize = resources.getDimensionPixelSize(R.dimen.bottom_ellipsis_height)

		// default baseColor is black
		val defaultBaseColor = Color.BLACK

		context.withStyledAttributes(attrs, R.styleable.MaterialEditText) {
			textColorStateList =
				getColorStateList(R.styleable.MaterialEditText_met_textColor)
			textColorHintStateList =
				getColorStateList(R.styleable.MaterialEditText_met_textColorHint)
			baseColor =
				getColor(R.styleable.MaterialEditText_met_baseColor, defaultBaseColor)

			val defaultPrimaryColor = getPrimaryColor(context, baseColor)

			primaryColor =
				getColor(R.styleable.MaterialEditText_met_primaryColor, defaultPrimaryColor)
			setFloatingLabelInternal(
				getInt(
					R.styleable.MaterialEditText_met_floatingLabel,
					0
				)
			)
			errorColor = getColor(
				R.styleable.MaterialEditText_met_errorColor,
				"#e7492E".toColorInt()
			)
			minCharacters = getInt(R.styleable.MaterialEditText_met_minCharacters, 0)
			maxCharacters = getInt(R.styleable.MaterialEditText_met_maxCharacters, 0)
			singleLineEllipsis =
				getBoolean(R.styleable.MaterialEditText_met_singleLineEllipsis, false)
			helperText = getString(R.styleable.MaterialEditText_met_helperText)
			helperTextColor = getColor(R.styleable.MaterialEditText_met_helperTextColor, -1)
			minBottomTextLines =
				getInt(R.styleable.MaterialEditText_met_minBottomTextLines, 0)
			val fontIdForAccent =
				getResourceId(R.styleable.MaterialEditText_met_accentFont, 0)
			val fontPathForAccent =
				getString(R.styleable.MaterialEditText_met_accentTypeface)
			if (fontIdForAccent != 0) {
				textPaint.typeface = ResourcesCompat.getFont(context, fontIdForAccent)
			} else if (fontPathForAccent != null && !isInEditMode) {
				accentTypeface = getCustomTypeface(fontPathForAccent)
				textPaint.typeface = accentTypeface
			}
			val fontIdForView =
				getResourceId(R.styleable.MaterialEditText_android_fontFamily, 0)
			val fontPathForView = getString(R.styleable.MaterialEditText_met_typeface)
			if (fontIdForView != 0) {
				setTypeface(ResourcesCompat.getFont(context, fontIdForView))
			} else if (fontPathForView != null && !isInEditMode) {
				val typeface = getCustomTypeface(fontPathForView)
				setTypeface(typeface)
			}
			floatingLabelText = getString(R.styleable.MaterialEditText_met_floatingLabelText)
			if (floatingLabelText == null) {
				floatingLabelText = hint
			}
			floatingLabelPadding = getDimensionPixelSize(
				R.styleable.MaterialEditText_met_floatingLabelPadding,
				bottomSpacing
			)
			floatingLabelTextSize = getDimensionPixelSize(
				R.styleable.MaterialEditText_met_floatingLabelTextSize,
				resources.getDimensionPixelSize(R.dimen.floating_label_text_size)
			)
			floatingLabelTextColor =
				getColor(R.styleable.MaterialEditText_met_floatingLabelTextColor, -1)
			isFloatingLabelAnimating =
				getBoolean(R.styleable.MaterialEditText_met_floatingLabelAnimating, true)
			bottomTextSize = getDimensionPixelSize(
				R.styleable.MaterialEditText_met_bottomTextSize,
				resources.getDimensionPixelSize(R.dimen.bottom_text_size)
			)
			hideUnderline = getBoolean(R.styleable.MaterialEditText_met_hideUnderline, false)
			underlineColor = getColor(R.styleable.MaterialEditText_met_underlineColor, -1)
			autoValidate = getBoolean(R.styleable.MaterialEditText_met_autoValidate, false)
			iconLeftBitmaps = generateIconBitmaps(
				getResourceId(
					R.styleable.MaterialEditText_met_iconLeft,
					-1
				)
			)
			iconRightBitmaps = generateIconBitmaps(
				getResourceId(
					R.styleable.MaterialEditText_met_iconRight,
					-1
				)
			)
			showClearButton = getBoolean(R.styleable.MaterialEditText_met_clearButton, false)
			clearButtonBitmaps = generateIconBitmaps(R.drawable.met_ic_clear)
			iconPadding = getDimensionPixelSize(
				R.styleable.MaterialEditText_met_iconPadding,
				getPixel(16)
			)
			floatingLabelAlwaysShown =
				getBoolean(R.styleable.MaterialEditText_met_floatingLabelAlwaysShown, false)
			helperTextAlwaysShown =
				getBoolean(R.styleable.MaterialEditText_met_helperTextAlwaysShown, false)
			isValidateOnFocusLost =
				getBoolean(R.styleable.MaterialEditText_met_validateOnFocusLost, false)
			checkCharactersCountAtBeginning = getBoolean(
				R.styleable.MaterialEditText_met_checkCharactersCountAtBeginning,
				true
			)
			val padding = getDimensionPixelSize(R.styleable.MaterialEditText_met_padding, 0)
			innerPaddingLeft =
				getDimensionPixelSize(R.styleable.MaterialEditText_met_padding_left, padding)
			innerPaddingTop =
				getDimensionPixelSize(R.styleable.MaterialEditText_met_padding_top, padding)
			innerPaddingRight = getDimensionPixelSize(
				R.styleable.MaterialEditText_met_padding_right,
				padding
			)
			innerPaddingBottom = getDimensionPixelSize(
				R.styleable.MaterialEditText_met_padding_bottom,
				padding
			)
		}

		background = null
		if (singleLineEllipsis) {
			val transformationMethod = getTransformationMethod()
			setSingleLine()
			setTransformationMethod(transformationMethod)
		}
		initMinBottomLines()
		initPadding()
		initText()
		initFloatingLabel()
		initTextWatcher()
		checkCharactersCount()
	}

	private fun getPrimaryColor(context: Context, baseColor: Int): Int {
		val typedValue = TypedValue()
		if (context.theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)) {
			return typedValue.data
		}
		return baseColor
	}

	private fun initText() {
		if (!TextUtils.isEmpty(text)) {
			val text: CharSequence = getText()
			setText(null)
			resetHintTextColor()
			setText(text)
			setSelection(text.length)
			floatingLabelFraction = 1f
			floatingLabelShown = true
		} else {
			resetHintTextColor()
		}
		resetTextColor()
	}

	private fun initTextWatcher() {
		addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			}

			override fun afterTextChanged(s: Editable?) {
				checkCharactersCount()
				if (autoValidate) {
					validate()
				} else {
					setError(null)
				}
				postInvalidate()
			}
		})
	}

	private fun getCustomTypeface(fontPath: String): Typeface? {
		return Typeface.createFromAsset(context.assets, fontPath)
	}

	fun setIconLeft(@DrawableRes res: Int) {
		iconLeftBitmaps = generateIconBitmaps(res)
		initPadding()
	}

	fun setIconLeft(drawable: Drawable?) {
		iconLeftBitmaps = generateIconBitmaps(drawable)
		initPadding()
	}

	fun setIconLeft(bitmap: Bitmap?) {
		iconLeftBitmaps = generateIconBitmaps(bitmap)
		initPadding()
	}

	fun setIconRight(@DrawableRes res: Int) {
		iconRightBitmaps = generateIconBitmaps(res)
		initPadding()
	}

	fun setIconRight(drawable: Drawable?) {
		iconRightBitmaps = generateIconBitmaps(drawable)
		initPadding()
	}

	fun setIconRight(bitmap: Bitmap?) {
		iconRightBitmaps = generateIconBitmaps(bitmap)
		initPadding()
	}

	fun isShowClearButton(): Boolean {
		return showClearButton
	}

	fun setShowClearButton(show: Boolean) {
		showClearButton = show
		correctPaddings()
	}

	private fun generateIconBitmaps(@DrawableRes origin: Int): Array<Bitmap?>? {
		if (origin == -1) {
			return null
		}
		val options = BitmapFactory.Options()
		options.inJustDecodeBounds = true
		BitmapFactory.decodeResource(resources, origin, options)
		val size = max(options.outWidth.toDouble(), options.outHeight.toDouble()).toInt()
		options.inSampleSize = if (size > iconSize) size / iconSize else 1
		options.inJustDecodeBounds = false
		return generateIconBitmaps(BitmapFactory.decodeResource(resources, origin, options))
	}

	private fun generateIconBitmaps(drawable: Drawable?): Array<Bitmap?>? {
		drawable?.let {
			val bitmap: Bitmap = createBitmap(it.intrinsicWidth, it.intrinsicHeight)
			val canvas = Canvas(bitmap)
			it.setBounds(0, 0, canvas.width, canvas.height)
			it.draw(canvas)
			return generateIconBitmaps(bitmap.scale(iconSize, iconSize, false))
		} ?: run { return null }
	}

	private fun generateIconBitmaps(originBitmap: Bitmap?): Array<Bitmap?>? {
		originBitmap?.let {
			val iconBitmaps: Array<Bitmap?> = arrayOfNulls(4)
			val scaledBitmap = scaleIcon(it)
			iconBitmaps[0] = createIconBitmap(scaledBitmap, baseColor, isLight(baseColor))
			iconBitmaps[1] = createIconBitmap(scaledBitmap, primaryColor)
			iconBitmaps[2] =
				createIconBitmap(scaledBitmap, baseColor, isLight(baseColor), alpha = 0x4c000000)
			iconBitmaps[3] = createIconBitmap(scaledBitmap, errorColor)

			return iconBitmaps
		} ?: return null
	}

	private fun createIconBitmap(
		original: Bitmap,
		color: Int,
		isLight: Boolean = false,
		alpha: Int = 0
	): Bitmap {
		val modifiedColor = if (isLight) color and 0x00ffffff or (-0x1000000)
		else color and 0x00ffffff or alpha

		val iconBitmap = original.copy(Bitmap.Config.ARGB_8888, true)
		val canvas = Canvas(iconBitmap)
		canvas.drawColor(modifiedColor, PorterDuff.Mode.SRC_IN)
		return iconBitmap
	}

	private fun scaleIcon(origin: Bitmap): Bitmap {
		val width = origin.width
		val height = origin.height
		val size = max(width.toDouble(), height.toDouble()).toInt()
		if (size == iconSize) {
			return origin
		} else if (size > iconSize) {
			val scaledWidth: Int
			val scaledHeight: Int
			if (width > iconSize) {
				scaledWidth = iconSize
				scaledHeight = (iconSize * (height.toFloat() / width)).toInt()
			} else {
				scaledHeight = iconSize
				scaledWidth = (iconSize * (width.toFloat() / height)).toInt()
			}
			return origin.scale(scaledWidth, scaledHeight, false)
		} else {
			return origin
		}
	}

	fun getFloatingLabelFraction(): Float {
		return floatingLabelFraction
	}

	fun setFloatingLabelFraction(floatingLabelFraction: Float) {
		this.floatingLabelFraction = floatingLabelFraction
		invalidate()
	}

	fun getFocusFraction(): Float {
		return focusFraction
	}

	fun setFocusFraction(focusFraction: Float) {
		this.focusFraction = focusFraction
		invalidate()
	}

	fun getCurrentBottomLines(): Float {
		return currentBottomLines
	}

	fun setCurrentBottomLines(currentBottomLines: Float) {
		this.currentBottomLines = currentBottomLines
		initPadding()
	}

	fun isFloatingLabelAlwaysShown(): Boolean {
		return floatingLabelAlwaysShown
	}

	fun setFloatingLabelAlwaysShown(floatingLabelAlwaysShown: Boolean) {
		this.floatingLabelAlwaysShown = floatingLabelAlwaysShown
		invalidate()
	}

	fun isHelperTextAlwaysShown(): Boolean {
		return helperTextAlwaysShown
	}

	fun setHelperTextAlwaysShown(helperTextAlwaysShown: Boolean) {
		this.helperTextAlwaysShown = helperTextAlwaysShown
		invalidate()
	}

	fun getAccentTypeface(): Typeface? {
		return accentTypeface
	}

	/**
	 * Set typeface used for the accent texts (floating label, error/helper text, character counter, etc.)
	 */
	fun setAccentTypeface(accentTypeface: Typeface?) {
		this.accentTypeface = accentTypeface
		this.textPaint.typeface = accentTypeface
		postInvalidate()
	}

	fun isHideUnderline(): Boolean {
		return hideUnderline
	}

	/**
	 * Set whether or not to hide the underline (shown by default).
	 *
	 *
	 * The positions of text below will be adjusted accordingly (error/helper text, character counter, ellipses, etc.)
	 *
	 *
	 * NOTE: You probably don't want to hide this if you have any subtext features of this enabled, as it can look weird to not have a dividing line between them.
	 */
	fun setHideUnderline(hideUnderline: Boolean) {
		this.hideUnderline = hideUnderline
		initPadding()
		postInvalidate()
	}

	/**
	 * get the color of the underline for normal state
	 */
	fun getUnderlineColor(): Int {
		return underlineColor
	}

	/**
	 * Set the color of the underline for normal state
	 *
	 * @param color
	 */
	fun setUnderlineColor(color: Int) {
		this.underlineColor = color
		postInvalidate()
	}

	fun getFloatingLabelText(): CharSequence? {
		return floatingLabelText
	}

	/**
	 * Set the floating label text.
	 *
	 *
	 * Pass null to force fallback to use hint's value.
	 *
	 * @param floatingLabelText
	 */
	fun setFloatingLabelText(floatingLabelText: CharSequence?) {
		this.floatingLabelText = floatingLabelText ?: hint
		postInvalidate()
	}

	fun getFloatingLabelTextSize(): Int {
		return floatingLabelTextSize
	}

	fun setFloatingLabelTextSize(size: Int) {
		floatingLabelTextSize = size
		initPadding()
	}

	fun getFloatingLabelTextColor(): Int {
		return floatingLabelTextColor
	}

	fun setFloatingLabelTextColor(color: Int) {
		this.floatingLabelTextColor = color
		postInvalidate()
	}

	fun getBottomTextSize(): Int {
		return bottomTextSize
	}

	fun setBottomTextSize(size: Int) {
		bottomTextSize = size
		initPadding()
	}

	private fun getPixel(dp: Int): Int {
		return dp2px(context, dp.toFloat())
	}

	private fun initPadding() {
		extraPaddingTop =
			if (floatingLabelEnabled) floatingLabelTextSize + floatingLabelPadding else floatingLabelPadding
		textPaint.textSize = bottomTextSize.toFloat()
		val textMetrics = textPaint.fontMetrics
		extraPaddingBottom =
			((textMetrics.descent - textMetrics.ascent) * currentBottomLines).toInt() + (if (hideUnderline) bottomSpacing else bottomSpacing * 2)
		extraPaddingLeft = if (iconLeftBitmaps == null) 0 else (iconOuterWidth + iconPadding)
		extraPaddingRight = if (iconRightBitmaps == null) 0 else (iconOuterWidth + iconPadding)
		correctPaddings()
	}

	/**
	 * calculate [.minBottomLines]
	 */
	private fun initMinBottomLines() {
		val extendBottom =
			minCharacters > 0 || maxCharacters > 0 || singleLineEllipsis || tempErrorText != null || helperText != null
		minBottomLines =
			if (minBottomTextLines > 0) minBottomTextLines else if (extendBottom) 1 else 0
		currentBottomLines = minBottomLines.toFloat()
	}

	/**
	 * use [.setPaddings] instead, or the paddingTop and the paddingBottom may be set incorrectly.
	 */
	@Deprecated("")
	override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
		super.setPadding(left, top, right, bottom)
	}

	/**
	 * Use this method instead of [.setPadding] to automatically set the paddingTop and the paddingBottom correctly.
	 */
	fun setPaddings(left: Int, top: Int, right: Int, bottom: Int) {
		innerPaddingTop = top
		innerPaddingBottom = bottom
		innerPaddingLeft = left
		innerPaddingRight = right
		correctPaddings()
	}

	/**
	 * Set paddings to the correct values
	 */
	private fun correctPaddings() {
		var buttonsWidthLeft = 0
		var buttonsWidthRight = 0
		val buttonsWidth = iconOuterWidth * this.buttonsCount
		if (this.isRTL) {
			buttonsWidthLeft = buttonsWidth
		} else {
			buttonsWidthRight = buttonsWidth
		}
		super.setPadding(
			innerPaddingLeft + extraPaddingLeft + buttonsWidthLeft,
			innerPaddingTop + extraPaddingTop,
			innerPaddingRight + extraPaddingRight + buttonsWidthRight,
			innerPaddingBottom + extraPaddingBottom
		)
	}

	private val buttonsCount: Int
		get() = if (isShowClearButton()) 1 else 0

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		if (!firstShown) {
			firstShown = true
		}
	}

	override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
		super.onLayout(changed, left, top, right, bottom)
		if (changed) {
			adjustBottomLines()
		}
	}

	/**
	 * @return True, if adjustments were made that require the view to be invalidated.
	 */
	private fun adjustBottomLines(): Boolean {
		// Bail out if we have a zero width; lines will be adjusted during next layout.
		if (width == 0) {
			return false
		}
		val destBottomLines: Int
		textPaint.textSize = bottomTextSize.toFloat()
		if (tempErrorText != null || helperText != null) {
			val alignment =
				if ((gravity and Gravity.RIGHT) == Gravity.RIGHT || this.isRTL) Layout.Alignment.ALIGN_OPPOSITE else if ((gravity and Gravity.LEFT) == Gravity.LEFT) Layout.Alignment.ALIGN_NORMAL else Layout.Alignment.ALIGN_CENTER
			textLayout = StaticLayout(
				if (tempErrorText != null) tempErrorText else helperText,
				textPaint,
				width - this.bottomTextLeftOffset - this.bottomTextRightOffset - paddingLeft - paddingRight,
				alignment,
				1.0f,
				0.0f,
				true
			)
			destBottomLines =
				max(textLayout!!.lineCount.toDouble(), minBottomTextLines.toDouble()).toInt()
		} else {
			destBottomLines = minBottomLines
		}
		if (bottomLines != destBottomLines.toFloat()) {
			getBottomLinesAnimator(destBottomLines.toFloat())!!.start()
		}
		bottomLines = destBottomLines.toFloat()
		return true
	}

	private fun initFloatingLabel() {
		// observe the text changing
		addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			}

			override fun afterTextChanged(s: Editable) {
				if (floatingLabelEnabled) {
					if (s.isEmpty()) {
						if (floatingLabelShown) {
							floatingLabelShown = false
							labelAnimator!!.reverse()
						}
					} else if (!floatingLabelShown) {
						floatingLabelShown = true
						labelAnimator!!.start()
					}
				}
			}
		})
		// observe the focus state to animate the floating label's text color appropriately
		innerFocusChangeListener = object : OnFocusChangeListener {
			override fun onFocusChange(v: View?, hasFocus: Boolean) {
				if (floatingLabelEnabled && highlightFloatingLabel) {
					if (hasFocus) {
						labelFocusAnimator!!.start()
					} else {
						labelFocusAnimator!!.reverse()
					}
				}
				if (isValidateOnFocusLost && !hasFocus) {
					validate()
				}
				if (outerFocusChangeListener != null) {
					outerFocusChangeListener!!.onFocusChange(v, hasFocus)
				}
			}
		}
		super.setOnFocusChangeListener(innerFocusChangeListener)
	}

	fun setBaseColor(color: Int) {
		if (baseColor != color) {
			baseColor = color
		}

		initText()

		postInvalidate()
	}

	fun setPrimaryColor(color: Int) {
		primaryColor = color
		postInvalidate()
	}

	/**
	 * Same function as [.setTextColor]. (Directly overriding the built-in one could cause some error, so use this method instead.)
	 */
	fun setMetTextColor(color: Int) {
		textColorStateList = ColorStateList.valueOf(color)
		resetTextColor()
	}

	/**
	 * Same function as [.setTextColor]. (Directly overriding the built-in one could cause some error, so use this method instead.)
	 */
	fun setMetTextColor(colors: ColorStateList?) {
		textColorStateList = colors
		resetTextColor()
	}

	private fun resetTextColor() {
		if (textColorStateList == null) {
			textColorStateList = ColorStateList(
				arrayOf<IntArray?>(
					intArrayOf(android.R.attr.state_enabled),
					EMPTY_STATE_SET
				),
				intArrayOf(
					baseColor and 0x00ffffff or -0x21000000,
					baseColor and 0x00ffffff or 0x44000000
				)
			)
			setTextColor(textColorStateList)
		} else {
			setTextColor(textColorStateList)
		}
	}

	/**
	 * Same function as [.setHintTextColor]. (The built-in one is a final method that can't be overridden, so use this method instead.)
	 */
	fun setMetHintTextColor(color: Int) {
		textColorHintStateList = ColorStateList.valueOf(color)
		resetHintTextColor()
	}

	/**
	 * Same function as [.setHintTextColor]. (The built-in one is a final method that can't be overridden, so use this method instead.)
	 */
	fun setMetHintTextColor(colors: ColorStateList?) {
		textColorHintStateList = colors
		resetHintTextColor()
	}

	private fun resetHintTextColor() {
		if (textColorHintStateList == null) {
			setHintTextColor(baseColor and 0x00ffffff or 0x44000000)
		} else {
			setHintTextColor(textColorHintStateList)
		}
	}

	private fun setFloatingLabelInternal(mode: Int) {
		when (fromInt(mode)) {
			FloatingLabelType.NORMAL -> {
				floatingLabelEnabled = true
				highlightFloatingLabel = false
			}

			FloatingLabelType.HIGHLIGHT -> {
				floatingLabelEnabled = true
				highlightFloatingLabel = true
			}

			else -> {
				floatingLabelEnabled = false
				highlightFloatingLabel = false
			}
		}
	}

	fun setFloatingLabel(mode: Int) {
		setFloatingLabelInternal(mode)
		initPadding()
	}

	fun getFloatingLabelPadding(): Int {
		return floatingLabelPadding
	}

	fun setFloatingLabelPadding(padding: Int) {
		floatingLabelPadding = padding
		postInvalidate()
	}

	fun setSingleLineEllipsis() {
		setSingleLineEllipsis(true)
	}

	fun setSingleLineEllipsis(enabled: Boolean) {
		singleLineEllipsis = enabled
		initMinBottomLines()
		initPadding()
		postInvalidate()
	}

	fun getMaxCharacters(): Int {
		return maxCharacters
	}

	fun setMaxCharacters(max: Int) {
		maxCharacters = max
		initMinBottomLines()
		initPadding()
		postInvalidate()
	}

	fun getMinCharacters(): Int {
		return minCharacters
	}

	fun setMinCharacters(min: Int) {
		minCharacters = min
		initMinBottomLines()
		initPadding()
		postInvalidate()
	}

	fun getMinBottomTextLines(): Int {
		return minBottomTextLines
	}

	fun setMinBottomTextLines(lines: Int) {
		minBottomTextLines = lines
		initMinBottomLines()
		initPadding()
		postInvalidate()
	}

	fun isAutoValidate(): Boolean {
		return autoValidate
	}

	fun setAutoValidate(autoValidate: Boolean) {
		this.autoValidate = autoValidate
		if (autoValidate) {
			validate()
		}
	}

	fun getErrorColor(): Int {
		return errorColor
	}

	fun setErrorColor(color: Int) {
		errorColor = color
		postInvalidate()
	}

	fun setHelperText(helperText: CharSequence?) {
		this.helperText = helperText?.toString()
		if (adjustBottomLines()) {
			postInvalidate()
		}
	}

	fun getHelperText(): String? {
		return helperText
	}

	fun getHelperTextColor(): Int {
		return helperTextColor
	}

	fun setHelperTextColor(color: Int) {
		helperTextColor = color
		postInvalidate()
	}

	override fun setError(errorText: CharSequence?) {
		tempErrorText = errorText?.toString()
		if (adjustBottomLines()) {
			postInvalidate()
		}
	}

	override fun getError(): CharSequence? {
		return tempErrorText
	}

	private val isInternalValid: Boolean
		/**
		 * only used to draw the bottom line
		 */
		get() = tempErrorText == null && this.isCharactersCountValid

	//    /**
	//     * if the main text matches the regex
	//     *
	//     * @deprecated use the new validator interface to add your own custom validator
	//     */
	//    @Deprecated
	//    public boolean isValid(String regex) {
	//        if (regex == null) {
	//            return false;
	//        }
	//        Pattern pattern = Pattern.compile(regex);
	//        Matcher matcher = pattern.matcher(getText());
	//        return matcher.matches();
	//    }
	//
	//    /**
	//     * check if the main text matches the regex, and set the error text if not.
	//     *
	//     * @return true if it matches the regex, false if not.
	//     * @deprecated use the new validator interface to add your own custom validator
	//     */
	//    @Deprecated
	//    public boolean validate(String regex, CharSequence errorText) {
	//        boolean isValid = isValid(regex);
	//        if (!isValid) {
	//            setError(errorText);
	//        }
	//        postInvalidate();
	//        return isValid;
	//    }
	/**
	 * Run validation on a single validator instance
	 *
	 * @param validator Validator to check
	 * @return True if valid, false if not
	 */
	fun validateWith(validator: METValidator): Boolean {
		val text: CharSequence = getText()
		val isValid = validator.isValid(text, text.isEmpty())
		if (!isValid) {
			setError(validator.errorMessage)
		}
		postInvalidate()
		return isValid
	}

	/**
	 * Check all validators, sets the error text if not
	 *
	 *
	 * NOTE: this stops at the first validator to report invalid.
	 *
	 * @return True if all validators pass, false if not
	 */
	fun validate(): Boolean {
		validators?.let {
			val text: CharSequence? = text
			val isTextEmpty = text.isNullOrEmpty()

			for (validator in it) {
				val isValid = validator.isValid(text ?: "", isTextEmpty)
				if (!isValid) {
					error = validator.errorMessage
					postInvalidate()
					return false
				}
			}
			setError(null)
			postInvalidate()

			return true
		} ?: run { return true }
	}

	fun hasValidators(): Boolean {
		return this.validators != null && !this.validators!!.isEmpty()
	}

	/**
	 * Adds a new validator to the View's list of validators
	 *
	 *
	 * This will be checked with the others in [.validate]
	 *
	 * @param validator Validator to add
	 * @return This instance, for easy chaining
	 */
	fun addValidator(validator: METValidator?): MaterialAutoCompleteTextViewK {
		if (validators == null) {
			this.validators = ArrayList<METValidator>()
		}
		this.validators!!.add(validator!!)
		return this
	}

	fun clearValidators() {
		if (this.validators != null) {
			this.validators!!.clear()
		}
	}

	fun setLengthChecker(lengthChecker: METLengthChecker?) {
		this.lengthChecker = lengthChecker
	}

	override fun setOnFocusChangeListener(listener: OnFocusChangeListener?) {
		if (innerFocusChangeListener == null) {
			super.setOnFocusChangeListener(listener)
		} else {
			outerFocusChangeListener = listener
		}
	}

	private fun getBottomLinesAnimator(destBottomLines: Float): ObjectAnimator? {
		if (bottomLinesAnimator == null) {
			bottomLinesAnimator =
				ObjectAnimator.ofFloat(this, "currentBottomLines", destBottomLines)
		} else {
			bottomLinesAnimator!!.cancel()
			bottomLinesAnimator!!.setFloatValues(destBottomLines)
		}
		return bottomLinesAnimator
	}

	override fun onDraw(canvas: Canvas) {
		val startX =
			scrollX + (if (iconLeftBitmaps == null) 0 else (iconOuterWidth + iconPadding))
		val endX =
			scrollX + (if (iconRightBitmaps == null) width else width - iconOuterWidth - iconPadding)
		var lineStartY = scrollY + height - paddingBottom

		// draw the icon(s)
		paint.alpha = 255
		iconLeftBitmaps?.let {
			val icon: Bitmap? =
				it[if (!isInternalValid) 3 else if (!isEnabled) 2 else if (hasFocus()) 1 else 0]
			icon?.let { ic ->
				val iconLeft: Int =
					startX - iconPadding - iconOuterWidth + (iconOuterWidth - ic.width) / 2
				val iconTop: Int =
					lineStartY + bottomSpacing - iconOuterHeight + (iconOuterHeight - ic.height) / 2
				canvas.drawBitmap(ic, iconLeft.toFloat(), iconTop.toFloat(), paint)
			}
		}
		iconRightBitmaps?.let {
			val icon: Bitmap? =
				it[if (!isInternalValid) 3 else if (!isEnabled) 2 else if (hasFocus()) 1 else 0]
			icon?.let { ic ->
				val iconRight: Int = endX + iconPadding + (iconOuterWidth - ic.width) / 2
				val iconTop: Int =
					lineStartY + bottomSpacing - iconOuterHeight + (iconOuterHeight - ic.height) / 2
				canvas.drawBitmap(ic, iconRight.toFloat(), iconTop.toFloat(), paint)
			}
		}

		// draw the clear button
		if (hasFocus() && showClearButton && !TextUtils.isEmpty(text) && isEnabled) {
			paint.alpha = 255
			var buttonLeft = if (isRTL) startX
			else endX - iconOuterWidth
			val clearButtonBitmap = clearButtonBitmaps?.get(0)
			clearButtonBitmap?.let {
				buttonLeft += (iconOuterWidth - it.width) / 2
				val iconTop: Int =
					lineStartY + bottomSpacing - iconOuterHeight + (iconOuterHeight - it.height) / 2
				canvas.drawBitmap(it, buttonLeft.toFloat(), iconTop.toFloat(), paint)
			}
		}

		// draw the underline
		if (!hideUnderline) {
			lineStartY += bottomSpacing
			if (!this.isInternalValid) { // not valid
				paint.color = errorColor
				canvas.drawRect(
					startX.toFloat(),
					lineStartY.toFloat(),
					endX.toFloat(),
					(lineStartY + getPixel(2)).toFloat(),
					paint
				)
			} else if (!isEnabled) { // disabled
				paint.color =
					if (underlineColor != -1) underlineColor else baseColor and 0x00ffffff or 0x44000000
				val interval = getPixel(1).toFloat()
				var xOffset = 0f
				while (xOffset < width) {
					canvas.drawRect(
						startX + xOffset,
						lineStartY.toFloat(),
						startX + xOffset + interval,
						(lineStartY + getPixel(1)).toFloat(),
						paint
					)
					xOffset += interval * 3
				}
			} else if (hasFocus()) { // focused
				paint.color = primaryColor
				canvas.drawRect(
					startX.toFloat(),
					lineStartY.toFloat(),
					endX.toFloat(),
					(lineStartY + getPixel(2)).toFloat(),
					paint
				)
			} else { // normal
				paint.color =
					if (underlineColor != -1) underlineColor else baseColor and 0x00ffffff or 0x1E000000
				canvas.drawRect(
					startX.toFloat(),
					lineStartY.toFloat(),
					endX.toFloat(),
					(lineStartY + getPixel(1)).toFloat(),
					paint
				)
			}
		}

		textPaint.textSize = bottomTextSize.toFloat()
		val textMetrics = textPaint.fontMetrics
		val relativeHeight = -textMetrics.ascent - textMetrics.descent
		val bottomTextPadding = bottomTextSize + textMetrics.ascent + textMetrics.descent

		// draw the characters counter
		if ((hasFocus() && hasCharactersCounter()) || !this.isCharactersCountValid) {
			textPaint.color =
				if (this.isCharactersCountValid) (baseColor and 0x00ffffff or 0x44000000) else errorColor
			val charactersCounterText = this.charactersCounterText
			canvas.drawText(
				charactersCounterText,
				if (this.isRTL) startX.toFloat() else endX - textPaint.measureText(
					charactersCounterText
				),
				lineStartY + bottomSpacing + relativeHeight,
				textPaint
			)
		}

		// draw the bottom text
		if (textLayout != null) {
			if (tempErrorText != null || ((helperTextAlwaysShown || hasFocus()) && !TextUtils.isEmpty(
					helperText
				))
			) { // error text or helper text
				textPaint.color =
					if (tempErrorText != null) errorColor else if (helperTextColor != -1) helperTextColor else (baseColor and 0x00ffffff or 0x44000000)
				canvas.withSave {
					if (isRTL) {
						translate(
							(endX - textLayout!!.width).toFloat(),
							lineStartY + bottomSpacing - bottomTextPadding
						)
					} else {
						translate(
							(startX + bottomTextLeftOffset).toFloat(),
							lineStartY + bottomSpacing - bottomTextPadding
						)
					}
					textLayout!!.draw(this)
				}
			}
		}

		// draw the floating label
		if (floatingLabelEnabled && !TextUtils.isEmpty(floatingLabelText)) {
			textPaint.textSize = floatingLabelTextSize.toFloat()
			// calculate the text color
			textPaint.color = (focusEvaluator.evaluate(
				focusFraction * (if (isEnabled) 1 else 0),
				if (floatingLabelTextColor != -1) floatingLabelTextColor else (baseColor and 0x00ffffff or 0x44000000),
				primaryColor
			) as Int?)!!

			// calculate the horizontal position
			val floatingLabelWidth = textPaint.measureText(floatingLabelText.toString())
			val floatingLabelStartX: Int =
				if ((gravity and Gravity.RIGHT) == Gravity.RIGHT || this.isRTL) {
					(endX - floatingLabelWidth).toInt()
				} else if ((gravity and Gravity.LEFT) == Gravity.LEFT) {
					startX
				} else {
					startX + (this.innerPaddingLeft + (width - this.innerPaddingLeft - this.innerPaddingRight - floatingLabelWidth) / 2).toInt()
				}

			// calculate the vertical position
			val distance = floatingLabelPadding
			val floatingLabelStartY =
				(innerPaddingTop + floatingLabelTextSize + floatingLabelPadding - distance * (if (floatingLabelAlwaysShown) 1f else floatingLabelFraction) + scrollY).toInt()

			// calculate the alpha
			val alpha =
				(((if (floatingLabelAlwaysShown) 1f else floatingLabelFraction) * 0xff * (0.74f * focusFraction * (if (isEnabled) 1 else 0) + 0.26f) * (if (floatingLabelTextColor != -1) 1f else Color.alpha(
					floatingLabelTextColor
				) / 256f)).toInt())
			textPaint.alpha = alpha

			// draw the floating label
			canvas.drawText(
				floatingLabelText.toString(),
				floatingLabelStartX.toFloat(),
				floatingLabelStartY.toFloat(),
				textPaint
			)
		}

		// draw the bottom ellipsis
		if (hasFocus() && singleLineEllipsis && scrollX != 0) {
			paint.color = if (this.isInternalValid) primaryColor else errorColor
			val startY = (lineStartY + bottomSpacing).toFloat()
			val ellipsisStartX: Int = if (this.isRTL) {
				endX
			} else {
				startX
			}
			val signum = if (this.isRTL) -1 else 1
			canvas.drawCircle(
				ellipsisStartX + (signum * bottomEllipsisSize).toFloat() / 2,
				startY + bottomEllipsisSize.toFloat() / 2,
				bottomEllipsisSize.toFloat() / 2,
				paint
			)
			canvas.drawCircle(
				ellipsisStartX + (signum * bottomEllipsisSize * 5).toFloat() / 2,
				startY + bottomEllipsisSize.toFloat() / 2,
				bottomEllipsisSize.toFloat() / 2,
				paint
			)
			canvas.drawCircle(
				ellipsisStartX + (signum * bottomEllipsisSize * 9).toFloat() / 2,
				startY + bottomEllipsisSize.toFloat() / 2,
				bottomEllipsisSize.toFloat() / 2,
				paint
			)
		}

		// draw the original things
		super.onDraw(canvas)
	}

	private val isRTL: Boolean
		get() {
			val config = resources.configuration
			return config.layoutDirection == LAYOUT_DIRECTION_RTL
		}

	private val bottomTextLeftOffset: Int
		get() = if (this.isRTL) this.charactersCounterWidth else this.bottomEllipsisWidth

	private val bottomTextRightOffset: Int
		get() = if (this.isRTL) this.bottomEllipsisWidth else this.charactersCounterWidth

	private val charactersCounterWidth: Int
		get() = if (hasCharactersCounter()) textPaint.measureText(this.charactersCounterText)
			.toInt() else 0

	private val bottomEllipsisWidth: Int
		get() = if (singleLineEllipsis) (bottomEllipsisSize * 5 + getPixel(4)) else 0

	private fun checkCharactersCount() {
		if ((!firstShown && !checkCharactersCountAtBeginning) || !hasCharactersCounter()) {
			isCharactersCountValid = true
			return
		}
		val text = text ?: ""
		val count = checkLength(text)
		isCharactersCountValid =
			(count >= minCharacters) && (maxCharacters <= 0 || count <= maxCharacters)
	}

	private fun hasCharactersCounter(): Boolean {
		return minCharacters > 0 || maxCharacters > 0
	}

	private val charactersCounterText: String
		get() {
			val text: String
			if (minCharacters <= 0) {
				text =
					if (this.isRTL) maxCharacters.toString() + " / " + checkLength(getText()) else checkLength(
						getText()
					).toString() + " / " + maxCharacters
			} else if (maxCharacters <= 0) {
				text =
					if (this.isRTL) "+" + minCharacters + " / " + checkLength(getText()) else checkLength(
						getText()
					).toString() + " / " + minCharacters + "+"
			} else {
				text =
					if (this.isRTL) "$maxCharacters-$minCharacters / " + checkLength(
						getText()
					) else checkLength(getText()).toString() + " / " + minCharacters + "-" + maxCharacters
			}
			return text
		}

	override fun performClick(): Boolean {
		Log.d("Namzzz", "MaterialAutoCompleteTextView: performClick")
		return super.performClick()
	}

	override fun onTouchEvent(event: MotionEvent): Boolean {
		performClick()
		if (singleLineEllipsis && scrollX > 0 && event.action == MotionEvent.ACTION_DOWN && event.x < getPixel(
				4 * 5
			) && event.y > height - extraPaddingBottom - innerPaddingBottom && event.y < height - innerPaddingBottom
		) {
			setSelection(0)
			return false
		}
		if (hasFocus() && showClearButton && isEnabled) {
			when (event.action) {
				MotionEvent.ACTION_DOWN -> {
					if (insideClearButton(event)) {
						clearButtonTouched = true
						clearButtonClicking = true
						return true
					}
					if (clearButtonClicking && !insideClearButton(event)) {
						clearButtonClicking = false
					}
					if (clearButtonTouched) {
						return true
					}
				}

				MotionEvent.ACTION_MOVE -> {
					if (clearButtonClicking && !insideClearButton(event)) {
						clearButtonClicking = false
					}
					if (clearButtonTouched) {
						return true
					}
				}

				MotionEvent.ACTION_UP -> {
					if (clearButtonClicking) {
						if (!TextUtils.isEmpty(text)) {
							setText(null)
						}
						clearButtonClicking = false
					}
					if (clearButtonTouched) {
						clearButtonTouched = false
						return true
					}
				}

				MotionEvent.ACTION_CANCEL -> {
					clearButtonTouched = false
					clearButtonClicking = false
				}
			}
		}
		return super.onTouchEvent(event)
	}

	private fun insideClearButton(event: MotionEvent): Boolean {
		val x = event.x
		val y = event.y
		val startX =
			scrollX + (if (iconLeftBitmaps == null) 0 else (iconOuterWidth + iconPadding))
		val endX =
			scrollX + (if (iconRightBitmaps == null) width else width - iconOuterWidth - iconPadding)
		val buttonLeft: Int = if (this.isRTL) {
			startX
		} else {
			endX - iconOuterWidth
		}
		val buttonTop =
			scrollY + height - paddingBottom + bottomSpacing - iconOuterHeight
		return (x >= buttonLeft && x < buttonLeft + iconOuterWidth && y >= buttonTop && y < buttonTop + iconOuterHeight)
	}

	private fun checkLength(text: CharSequence): Int {
		if (lengthChecker == null) return text.length
		return lengthChecker!!.getLength(text)
	}
}