package io.virgo_common.common_libs.customView.materialEdittext

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.TypedArray
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
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import io.virgo_common.common_libs.R
import io.virgo_common.common_libs.customView.materialEdittext.Colors.isLight
import io.virgo_common.common_libs.customView.materialEdittext.Density.dp2px
import io.virgo_common.common_libs.customView.materialEdittext.enums.FloatingLabelType
import io.virgo_common.common_libs.customView.materialEdittext.enums.FloatingLabelType.Companion.fromInt
import io.virgo_common.common_libs.customView.materialEdittext.validation.METLengthChecker
import io.virgo_common.common_libs.customView.materialEdittext.validation.METValidator
import kotlin.math.max

class MaterialEditTextK : AppCompatEditText {
	/**
	 * the spacing between the main text and the inner top padding.
	 */
	private var extraPaddingTop: Int = 0

	/**
	 * the spacing between the main text and the inner bottom padding.
	 */
	private var extraPaddingBottom: Int = 0

	/**
	 * the extra spacing between the main text and the left, actually for the left icon.
	 */
	private var extraPaddingLeft: Int = 0

	/**
	 * the extra spacing between the main text and the right, actually for the right icon.
	 */
	private var extraPaddingRight: Int = 0

	/**
	 * the floating label's text size.
	 */
	private var floatingLabelTextSize: Int = 0

	/**
	 * the floating label's text color.
	 */
	private var floatingLabelTextColor: Int = 0

	/**
	 * the bottom texts' size.
	 */
	private var bottomTextSize: Int = 0

	/**
	 * the spacing between the main text and the floating label.
	 */
	private var floatingLabelPadding: Int = 0

	/**
	 * the spacing between the main text and the bottom components (bottom ellipsis, helper/error text, characters counter).
	 */
	private var bottomSpacing: Int = 0

	/**
	 * whether the floating label should be shown. default is false.
	 */
	private var floatingLabelEnabled: Boolean = false

	/**
	 * whether to highlight the floating label's text color when focused (with the main color). default is true.
	 */
	private var highlightFloatingLabel: Boolean = false

	/**
	 * the base color of the line and the texts. default is black.
	 */
	private var baseColor: Int = 0

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
	private var primaryColor: Int = 0

	/**
	 * the color for when something is wrong.(e.g. exceeding max characters)
	 */
	private var errorColor: Int = 0

	/**
	 * min characters count limit. 0 means no limit. default is 0. NOTE: the character counter will increase the View's height.
	 */
	private var minCharacters: Int = 0

	/**
	 * max characters count limit. 0 means no limit. default is 0. NOTE: the character counter will increase the View's height.
	 */
	private var maxCharacters: Int = 0

	/**
	 * whether to show the bottom ellipsis in singleLine mode. default is false. NOTE: the bottom ellipsis will increase the View's height.
	 */
	private var singleLineEllipsis: Boolean = false

	/**
	 * Always show the floating label, instead of animating it in/out. False by default.
	 */
	private var floatingLabelAlwaysShown: Boolean = false

	/**
	 * Always show the helper text, no matter if the edit text is focused. False by default.
	 */
	private var helperTextAlwaysShown: Boolean = false

	/**
	 * bottom ellipsis's height
	 */
	private var bottomEllipsisSize: Int = 0

	/**
	 * min bottom lines count.
	 */
	private var minBottomLines: Int = 0

	/**
	 * reserved bottom text lines count, no matter if there is some helper/error text.
	 */
	private var minBottomTextLines: Int = 0

	/**
	 * real-time bottom lines count. used for bottom extending/collapsing animation.
	 */
	private var currentBottomLines: Float = 0f

	/**
	 * bottom lines count.
	 */
	private var bottomLines: Float = 0f

	/**
	 * Helper text at the bottom
	 */
	private var helperText: String? = null

	/**
	 * Helper text color
	 */
	private var helperTextColor: Int = -1

	/**
	 * error text for manually invoked [.setError]
	 */
	private var tempErrorText: String? = null

	/**
	 * animation fraction of the floating label (0 as totally hidden).
	 */
	private var floatingLabelFraction: Float = 0f

	/**
	 * whether the floating label is being shown.
	 */
	private var floatingLabelShown: Boolean = false

	/**
	 * the floating label's focusFraction
	 */
	private var focusFraction: Float = 0f

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
	private var hideUnderline: Boolean = false

	/**
	 * Underline's color
	 */
	private var underlineColor: Int = 0

	/**
	 * Whether to validate as soon as the text has changed. False by default
	 */
	private var autoValidate: Boolean = false

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
	private var checkCharactersCountAtBeginning: Boolean = false

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

	private var showClearButton: Boolean = false
	private var firstShown: Boolean = false
	private var iconSize: Int = 0
	private var iconOuterWidth: Int = 0
	private var iconOuterHeight: Int = 0
	private var iconPadding: Int = 0
	private var clearButtonTouched: Boolean = false
	private var clearButtonClicking: Boolean = false
	private var textColorStateList: ColorStateList? = null
	private var textColorHintStateList: ColorStateList? = null
	private val focusEvaluator: ArgbEvaluator = ArgbEvaluator()
	var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
	var textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
	var textLayout: StaticLayout? = null
	var labelAnimator: ObjectAnimator? = null
		get() {
			if (field == null) {
				field = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
			}
			field!!.setDuration((if (isFloatingLabelAnimating) 300 else 0).toLong())
			return field
		}
	var labelFocusAnimator: ObjectAnimator? = null
		get() {
			if (field == null) {
				field = ObjectAnimator.ofFloat(this, "focusFraction", 0f, 1f)
			}
			return field
		}
	private var bottomLinesAnimator: ObjectAnimator? = null
	var innerFocusChangeListener: OnFocusChangeListener? = null
	private var outerFocusChangeListener: OnFocusChangeListener? = null
	private var validators: MutableList<METValidator>? = null
	private var lengthChecker: METLengthChecker? = null

	constructor(context: Context) : super(context) {
		if (isInEditMode) {
			return
		}
		init(context, null)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		if (isInEditMode) {
			return
		}
		init(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
		if (isInEditMode) {
			return
		}
		init(context, attrs)
	}

	@SuppressLint("CustomViewStyleable")
	private fun init(context: Context, attrs: AttributeSet?) {
		iconSize = getPixel(32)
		iconOuterWidth = getPixel(48)
		iconOuterHeight = getPixel(32)

		bottomSpacing = resources.getDimensionPixelSize(R.dimen.inner_components_spacing)
		bottomEllipsisSize = resources.getDimensionPixelSize(R.dimen.bottom_ellipsis_height)

		// default baseColor is black
		val defaultBaseColor: Int = Color.BLACK

		val typedArray: TypedArray =
			context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
		textColorStateList =
			typedArray.getColorStateList(R.styleable.MaterialEditText_met_textColor)
		textColorHintStateList =
			typedArray.getColorStateList(R.styleable.MaterialEditText_met_textColorHint)
		baseColor =
			typedArray.getColor(R.styleable.MaterialEditText_met_baseColor, defaultBaseColor)

		val defaultPrimaryColor: Int = getPrimaryColor(context, baseColor)

		primaryColor =
			typedArray.getColor(R.styleable.MaterialEditText_met_primaryColor, defaultPrimaryColor)
		setFloatingLabelInternal(
			typedArray.getInt(
				R.styleable.MaterialEditText_met_floatingLabel,
				0
			)
		)
		errorColor = typedArray.getColor(
			R.styleable.MaterialEditText_met_errorColor,
			Color.parseColor("#e7492E")
		)
		minCharacters = typedArray.getInt(R.styleable.MaterialEditText_met_minCharacters, 0)
		maxCharacters = typedArray.getInt(R.styleable.MaterialEditText_met_maxCharacters, 0)
		singleLineEllipsis =
			typedArray.getBoolean(R.styleable.MaterialEditText_met_singleLineEllipsis, false)
		helperText = typedArray.getString(R.styleable.MaterialEditText_met_helperText)
		helperTextColor = typedArray.getColor(R.styleable.MaterialEditText_met_helperTextColor, -1)
		minBottomTextLines =
			typedArray.getInt(R.styleable.MaterialEditText_met_minBottomTextLines, 0)
		val fontIdForAccent: Int =
			typedArray.getResourceId(R.styleable.MaterialEditText_met_accentFont, 0)
		val fontPathForAccent: String? =
			typedArray.getString(R.styleable.MaterialEditText_met_accentTypeface)
		if (fontIdForAccent != 0) {
			textPaint.setTypeface(ResourcesCompat.getFont(context, fontIdForAccent))
		} else if (!isInEditMode) {
			fontPathForAccent?.let {
				accentTypeface = getCustomTypeface(it)
				textPaint.setTypeface(accentTypeface)
			}
		}
		val fontIdForView: Int =
			typedArray.getResourceId(R.styleable.MaterialEditText_android_fontFamily, 0)
		val fontPathForView: String? =
			typedArray.getString(R.styleable.MaterialEditText_met_typeface)
		if (fontIdForView != 0) {
			typeface = ResourcesCompat.getFont(context, fontIdForView)
		} else if (!isInEditMode) {
			fontPathForView?.let {
				val typeface: Typeface = getCustomTypeface(it)
				setTypeface(typeface)
			}
		}
		floatingLabelText = typedArray.getString(R.styleable.MaterialEditText_met_floatingLabelText)
		if (floatingLabelText == null) floatingLabelText = hint
		floatingLabelPadding = typedArray.getDimensionPixelSize(
			R.styleable.MaterialEditText_met_floatingLabelPadding,
			bottomSpacing
		)
		floatingLabelTextSize = typedArray.getDimensionPixelSize(
			R.styleable.MaterialEditText_met_floatingLabelTextSize,
			resources.getDimensionPixelSize(R.dimen.floating_label_text_size)
		)
		floatingLabelTextColor =
			typedArray.getColor(R.styleable.MaterialEditText_met_floatingLabelTextColor, -1)
		isFloatingLabelAnimating =
			typedArray.getBoolean(R.styleable.MaterialEditText_met_floatingLabelAnimating, true)
		bottomTextSize = typedArray.getDimensionPixelSize(
			R.styleable.MaterialEditText_met_bottomTextSize,
			resources.getDimensionPixelSize(R.dimen.bottom_text_size)
		)
		hideUnderline = typedArray.getBoolean(R.styleable.MaterialEditText_met_hideUnderline, false)
		underlineColor = typedArray.getColor(R.styleable.MaterialEditText_met_underlineColor, -1)
		autoValidate = typedArray.getBoolean(R.styleable.MaterialEditText_met_autoValidate, false)
		iconLeftBitmaps = generateIconBitmaps(
			typedArray.getResourceId(
				R.styleable.MaterialEditText_met_iconLeft,
				-1
			)
		)
		iconRightBitmaps = generateIconBitmaps(
			typedArray.getResourceId(
				R.styleable.MaterialEditText_met_iconRight,
				-1
			)
		)
		showClearButton = typedArray.getBoolean(R.styleable.MaterialEditText_met_clearButton, false)
		clearButtonBitmaps = generateIconBitmaps(R.mipmap.met_ic_clear)
		iconPadding = typedArray.getDimensionPixelSize(
			R.styleable.MaterialEditText_met_iconPadding,
			getPixel(16)
		)
		floatingLabelAlwaysShown =
			typedArray.getBoolean(R.styleable.MaterialEditText_met_floatingLabelAlwaysShown, false)
		helperTextAlwaysShown =
			typedArray.getBoolean(R.styleable.MaterialEditText_met_helperTextAlwaysShown, false)
		isValidateOnFocusLost =
			typedArray.getBoolean(R.styleable.MaterialEditText_met_validateOnFocusLost, false)
		checkCharactersCountAtBeginning = typedArray.getBoolean(
			R.styleable.MaterialEditText_met_checkCharactersCountAtBeginning,
			true
		)
		val padding = typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_padding, 0)
		innerPaddingLeft =
			typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_padding_left, padding)
		innerPaddingTop =
			typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_padding_top, padding)
		innerPaddingRight = typedArray.getDimensionPixelSize(
			R.styleable.MaterialEditText_met_padding_right,
			padding
		)
		innerPaddingBottom = typedArray.getDimensionPixelSize(
			R.styleable.MaterialEditText_met_padding_bottom,
			padding
		)
		typedArray.recycle()

		background = null
		if (singleLineEllipsis) {
			setSingleLine()
			transformationMethod = transformationMethod
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
			val text: CharSequence? = text
			setText(null)
			resetHintTextColor()
			setText(text)
			setSelection(text!!.length)
			floatingLabelFraction = 1f
			floatingLabelShown = true
		} else {
			resetHintTextColor()
		}
		resetTextColor()
	}

	private fun initTextWatcher() {
		addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
			}

			override fun afterTextChanged(s: Editable) {
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

	private fun getCustomTypeface(fontPath: String): Typeface {
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
		val options: BitmapFactory.Options = BitmapFactory.Options()
		options.inJustDecodeBounds = true
		BitmapFactory.decodeResource(resources, origin, options)
		val size: Int =
			max(options.outWidth.toDouble(), options.outHeight.toDouble()).toInt()
		options.inSampleSize = if (size > iconSize) size / iconSize else 1
		options.inJustDecodeBounds = false
		return generateIconBitmaps(BitmapFactory.decodeResource(resources, origin, options))
	}

	private fun generateIconBitmaps(drawable: Drawable?): Array<Bitmap?>? {
		drawable?.let {
			val bitmap: Bitmap = Bitmap.createBitmap(
				it.intrinsicWidth,
				it.intrinsicHeight,
				Bitmap.Config.ARGB_8888
			)
			val canvas = Canvas(bitmap)
			it.setBounds(0, 0, canvas.width, canvas.height)
			it.draw(canvas)
			return generateIconBitmaps(Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, false))
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
		val width: Int = origin.width
		val height: Int = origin.height
		val size: Int = max(width.toDouble(), height.toDouble()).toInt()
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
			return Bitmap.createScaledBitmap(origin, scaledWidth, scaledHeight, false)
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
		textPaint.setTypeface(accentTypeface)
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
		val textMetrics: Paint.FontMetrics = textPaint.fontMetrics
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
		val extendBottom: Boolean =
			minCharacters > 0 || maxCharacters > 0 || singleLineEllipsis || tempErrorText != null || helperText != null
		minBottomLines =
			if (minBottomTextLines > 0) minBottomTextLines else if (extendBottom) 1 else 0
		currentBottomLines = minBottomLines.toFloat()
	}

	/**
	 * use [.setPaddings] instead, or the paddingTop and the paddingBottom may be set incorrectly.
	 */
	@Deprecated(
		"", ReplaceWith(
			"super.setPadding(left, top, right, bottom)",
			"androidx.appcompat.widget.AppCompatEditText"
		)
	)
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
		val buttonsWidth: Int = iconOuterWidth * buttonsCount
		if (isRTL) {
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
		if (width == 0) return false
		val destBottomLines: Int
		textPaint.textSize = bottomTextSize.toFloat()
		if (tempErrorText != null || helperText != null) {
			val alignment: Layout.Alignment =
				if ((gravity and Gravity.RIGHT) == Gravity.RIGHT || isRTL) Layout.Alignment.ALIGN_OPPOSITE else if ((gravity and Gravity.LEFT) == Gravity.LEFT) Layout.Alignment.ALIGN_NORMAL else Layout.Alignment.ALIGN_CENTER
			textLayout = StaticLayout(
				if (tempErrorText != null) tempErrorText else helperText,
				textPaint,
				width - bottomTextLeftOffset - bottomTextRightOffset - paddingLeft - paddingRight,
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
			override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
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
		innerFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
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
				arrayOf(intArrayOf(android.R.attr.state_enabled), EMPTY_STATE_SET),
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

	override fun getError(): CharSequence {
		return tempErrorText!!
	}

	private val isInternalValid: Boolean
		/**
		 * only used to draw the bottom line
		 */
		get() = tempErrorText == null && isCharactersCountValid

	/**
	 * Run validation on a single validator instance
	 *
	 * @param validator Validator to check
	 * @return True if valid, false if not
	 */
	fun validateWith(validator: METValidator): Boolean {
		val text = text ?: ""
		val isValid: Boolean = validator.isValid(text, text.isEmpty())
		if (!isValid) {
			error = validator.errorMessage
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
		return !validators.isNullOrEmpty()
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
	fun addValidator(validator: METValidator): MaterialEditTextK {
		if (validators == null) {
			this.validators = ArrayList()
		}
		validators!!.add(validator)
		return this
	}

	fun clearValidators() {
		if (this.validators != null) {
			validators!!.clear()
		}
	}

	fun getValidators(): List<METValidator>? {
		return this.validators
	}

	fun setLengthChecker(lengthChecker: METLengthChecker?) {
		this.lengthChecker = lengthChecker
	}

	override fun setOnFocusChangeListener(listener: OnFocusChangeListener) {
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
			bottomLinesAnimator?.cancel()
			bottomLinesAnimator?.setFloatValues(destBottomLines)
		}
		return bottomLinesAnimator
	}

	override fun onDraw(canvas: Canvas) {
		val startX: Int =
			scrollX + (if (iconLeftBitmaps == null) 0 else (iconOuterWidth + iconPadding)) + paddingLeft
		val endX: Int =
			scrollX + (if (iconRightBitmaps == null) width else width - iconOuterWidth - iconPadding) - paddingRight
		var lineStartY: Int = scrollY + height - paddingBottom

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
			if (!isInternalValid) { // not valid
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
				val interval: Float = getPixel(1).toFloat()
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
		val textMetrics: Paint.FontMetrics = textPaint.fontMetrics
		val relativeHeight: Float = -textMetrics.ascent - textMetrics.descent
		val bottomTextPadding: Float = bottomTextSize + textMetrics.ascent + textMetrics.descent

		// draw the characters counter
		if ((hasFocus() && hasCharactersCounter()) || !isCharactersCountValid) {
			textPaint.color =
				if (isCharactersCountValid) (baseColor and 0x00ffffff or 0x44000000) else errorColor
			val charactersCounterText: String = charactersCounterText
			canvas.drawText(
				charactersCounterText,
				if (isRTL) startX.toFloat() else endX - textPaint.measureText(charactersCounterText),
				lineStartY + bottomSpacing + relativeHeight,
				textPaint
			)
		}

		// draw the bottom text
		textLayout?.let { layout ->
			val showErrorText = tempErrorText != null
			val showHelperText =
				(helperTextAlwaysShown || hasFocus()) && !TextUtils.isEmpty(helperText)

			if (showErrorText || showHelperText) {
				textPaint.color = when {
					showErrorText -> errorColor
					helperTextColor != -1 -> helperTextColor
					else -> baseColor and 0x00ffffff or 0x44000000
				}

				canvas.save()
				val translateX = if (isRTL) {
					(endX - layout.width).toFloat()
				} else {
					(startX + bottomTextLeftOffset).toFloat()
				}
				val translateY = (lineStartY + bottomSpacing - bottomTextPadding)

				canvas.translate(translateX, translateY)
				layout.draw(canvas)
				canvas.restore()
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
			val floatingLabelWidth: Float = textPaint.measureText(floatingLabelText.toString())
			val floatingLabelStartX =
				if ((gravity and Gravity.RIGHT) == Gravity.RIGHT || isRTL) (endX - floatingLabelWidth).toInt()
				else if ((gravity and Gravity.LEFT) == Gravity.LEFT) startX
				else startX + (innerPaddingLeft + (width - innerPaddingLeft - innerPaddingRight - floatingLabelWidth) / 2).toInt()

			// calculate the vertical position
			val distance: Int = floatingLabelPadding
			val floatingLabelStartY: Int =
				(innerPaddingTop + floatingLabelTextSize + floatingLabelPadding - distance * (if (floatingLabelAlwaysShown) 1f else floatingLabelFraction) + scrollY).toInt()

			// calculate the alpha
			val alpha: Int =
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
			paint.color = if (isInternalValid) primaryColor else errorColor
			val startY: Float = (lineStartY + bottomSpacing).toFloat()
			val ellipsisStartX = if (isRTL) {
				endX
			} else {
				startX
			}
			val signum: Int = if (isRTL) -1 else 1
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
			val config: Configuration = resources.configuration
			return config.layoutDirection == LAYOUT_DIRECTION_RTL
		}

	private val bottomTextLeftOffset: Int
		get() = if (isRTL) charactersCounterWidth else bottomEllipsisWidth

	private val bottomTextRightOffset: Int
		get() = if (isRTL) bottomEllipsisWidth else charactersCounterWidth

	private val charactersCounterWidth: Int
		get() = if (hasCharactersCounter()) textPaint.measureText(charactersCounterText)
			.toInt() else 0

	private val bottomEllipsisWidth: Int
		get() {
			return if (singleLineEllipsis) (bottomEllipsisSize * 5 + getPixel(4)) else 0
		}

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
			val currentLength = checkLength(text ?: "")
			return when {
				minCharacters <= 0 -> if (isRTL) "$maxCharacters / $currentLength" else "$currentLength / $maxCharacters"
				maxCharacters <= 0 -> if (isRTL) "+$minCharacters / $currentLength" else "$currentLength / $minCharacters+"
				else -> if (isRTL) "$maxCharacters-$minCharacters / $currentLength" else "$currentLength / $minCharacters-$maxCharacters"
			}
		}

	override fun performClick(): Boolean {
		Log.d("Namzzz", "MaterialEditTextK: performClick")
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
							text = null
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
		val (x, y) = event.x to event.y
		val startX = scrollX + (iconLeftBitmaps?.let { iconOuterWidth + iconPadding } ?: 0)
		val endX =
			scrollX + (iconRightBitmaps?.let { width - iconOuterWidth - iconPadding } ?: width)

		val buttonLeft = if (isRTL) startX else endX - iconOuterWidth
		val buttonTop = scrollY + height - paddingBottom + bottomSpacing - iconOuterHeight

		return (x >= buttonLeft && x <= buttonLeft + iconOuterWidth) && (y >= buttonTop && y <= buttonTop + iconOuterHeight)
//		val startX: Int =
//			scrollX + (if (iconLeftBitmaps == null) 0 else (iconOuterWidth + iconPadding))
//		val endX: Int =
//			scrollX + (if (iconRightBitmaps == null) width else width - iconOuterWidth - iconPadding)
//		val buttonLeft = if (isRTL) startX
//		else endX - iconOuterWidth
//		val buttonTop: Int = scrollY + height - paddingBottom + bottomSpacing - iconOuterHeight
//		return (x >= buttonLeft && x < buttonLeft + iconOuterWidth && y >= buttonTop && y < buttonTop + iconOuterHeight)
	}

	private fun checkLength(text: CharSequence): Int {
		if (lengthChecker == null) return text.length
		return lengthChecker!!.getLength(text)
	}
}